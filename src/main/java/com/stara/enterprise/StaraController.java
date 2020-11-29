package com.stara.enterprise;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.stara.enterprise.dto.Favorite;
import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.actor.ActorFeedItem;
import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import com.stara.enterprise.dto.show.ShowFeedItem;
import com.stara.enterprise.service.favorite.IFavoriteService;
import com.stara.enterprise.service.firebase.FirebaseService;
import com.stara.enterprise.service.firebase.IFirebaseService;
import com.stara.enterprise.service.review.IReviewService;
import com.stara.enterprise.service.schedule.IScheduleFeedService;
import com.stara.enterprise.service.show.IShowFeedService;
import com.stara.enterprise.service.actor.IActorFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class StaraController {
    @Autowired
    IFavoriteService favoriteService;

    @Autowired
    IShowFeedService showFeedService;

    @Autowired
    IActorFeedService actorFeedService;

    @Autowired
    IScheduleFeedService scheduleFeedService;

    @Autowired
    IFirebaseService firebaseService;

    @Autowired
    IReviewService reviewService;

    /**
     * GetMapping for / (root) endpoint
     * Equivalent of running https://www.tvmaze.com/schedule
     *
     * @param countryCode country to display schedule for
     * @param model used to pass schedule to UI layer
     * @return HTML page that displays schedule in an organized manner
     */
    @GetMapping("/")
    public String displaySchedule(
            @RequestParam(value = "countryCode", required = false) String countryCode,
            Model model,
            @CookieValue(value = "uid", required = false) String uid,
            HttpServletRequest request
    ) {
        try {
            String countryName = null;
            String ipAddress = request.getRemoteAddr();

            // User did not specify a country code, need to determine it via IP Address.
            if (countryCode == null) {
                String dbLocation = "assets/GeoLite2-Country.mmdb";

                // User is on localhost. Need to get IP Address via third party URL
                if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                    URL urlIpLookup = new URL("https://ip.derp.uk/");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlIpLookup.openStream()));
                    ipAddress = bufferedReader.readLine().trim();
                }

                // Read MaxMind Database
                File ipDbFile = new File(dbLocation);
                DatabaseReader ipDbReader = new DatabaseReader.Builder(ipDbFile).build();

                // Parse IP address into something MaxMind can use
                InetAddress inetAddress = InetAddress.getByName(ipAddress);
                // For IP address provided, store country related data from MaxMind database
                CountryResponse response = ipDbReader.country(inetAddress);

                countryCode = response.getCountry().getIsoCode();
                countryName = response.getCountry().getName();
            }
            // User specified country code, use it to determine country name
            else {
                Locale locale = new Locale("", countryCode);
                countryName = locale.getDisplayCountry();
            }
            System.out.println(ipAddress);

            List<ScheduleFeedItem> scheduleFeed = scheduleFeedService.fetchScheduleFeed(countryCode);
            model.addAttribute("scheduleFeed", scheduleFeed);
            model.addAttribute("countryName", countryName);
            model.addAttribute("uid", uid);
            return "schedule";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * GetMapping for /shows endpoint
     * Equivalent of running https://api.tvmaze.com/search/shows?q=searchShow
     *
     * @param searchShow show name to search for REQUIRED
     * @return search results from TVMaze API
     */
    @GetMapping("/shows")
    public ResponseEntity searchShows(@RequestParam(value = "searchShow", required = true) String searchShow, @CookieValue(value = "uid", required = false) String uid) {
        try {
            List<ShowFeedItem> showFeed = showFeedService.fetchShowFeed(searchShow);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(showFeed, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GetMapping for /actors endpoint
     * Equivalent of running https://api.tvmaze.com/search/people?q=searchActor
     *
     * @param searchActor actor name to search for REQUIRED
     * @return search results from TVMaze API
     */
    @GetMapping("/actors")
    public ResponseEntity searchActors(@RequestParam(value = "searchActor", required = true) String searchActor) {
        try {
            List<ActorFeedItem> actorFeed = actorFeedService.fetchActorFeed(searchActor);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(actorFeed, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GetMapping for /search endpoint
     * Equivalent of running https://www.tvmaze.com/search?q=searchTerm
     * Also equivalent of getting /shows and /actors results, combining them, and then displaying them via HTML
     * TVMaze API does not natively support searching for both shows and actors in one call so have to make two separate calls
     *
     * @param searchTerm show or actor to search for REQUIRED
     * @param model used to pass list of shows and actors to UI layer
     * @return HTML page that displays results in an organized manner
     */
    @GetMapping("/search")
    public String searchShowsAndActors(
            @RequestParam(value = "searchTerm", required = true) String searchTerm,
            Model model,
            @CookieValue(value = "uid", required = false) String uid
    ) {
        try {
            List<ShowFeedItem> shows = showFeedService.fetchShowFeed(searchTerm);
            List<ActorFeedItem> actors = actorFeedService.fetchActorFeed(searchTerm);
            model.addAttribute("showFeed", shows);
            model.addAttribute("actorFeed", actors);
            model.addAttribute("uid", uid);
            return "search";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * GetMapping for /schedule endpoint
     * Equivalent of running https://api.tvmaze.com/schedule?country=countryCode
     *
     * @param countryCode country to get schedule for REQUIRED
     * @return schedule from TVMaze API
     */
    @GetMapping("/schedule")
    public ResponseEntity getSchedule(@RequestParam(value = "countryCode", required = true) String countryCode) {
        try {
            List<ScheduleFeedItem> scheduleFeed = scheduleFeedService.fetchScheduleFeed(countryCode);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(scheduleFeed, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Reference: https://dzone.com/articles/how-to-use-cookies-in-spring-boot
    @GetMapping("/set-uid")
    public String setCookie(HttpServletResponse response, @RequestParam(value = "uid") String uid) {
        Cookie cookie = new Cookie("uid", uid);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/favorites";
    }

    // Reference: https://attacomsian.com/blog/cookies-spring-boot#deleting-cookie
    @GetMapping("/unset-uid")
    public String unsetCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("uid", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/favorites")
    public String fetchAllFavorites(@CookieValue(value = "uid", required = false) String uid, Model model) {
        try {
            if (uid == null) {
                System.out.println("No UID cookie found. User is not logged in.");
                return "login";
            }

            System.out.println("User is logged in. Fetching favorites and reviews.");
            List<Favorite> favorites = favoriteService.fetchAll(firebaseService.getUser(uid).getEmail());
            Map<String, Review> allReviewsForUid = reviewService.fetchReviewsByUid(uid);

            String displayName = firebaseService.getUser(uid).getDisplayName();

            model.addAttribute("displayName", displayName);
            model.addAttribute("favorites", favorites);
            model.addAttribute("uid", uid);
            model.addAttribute("reviews", allReviewsForUid);
            return "favorites";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    // Reference: https://stackoverflow.com/questions/2494774/how-to-explicitly-obtain-post-data-in-spring-mvc
    @PostMapping("/favorites/save/show")
    public String saveFavoriteShow(HttpServletRequest request, @CookieValue(value = "uid", required = false) String uid) {
        // User is not logged in, need to log in before show can be saved to favorites
        if (uid == null) {
            return "login";
        }

        // Need to manually differentiate ID between Actor & Show since TVMaze API doesn't do it for us
        String favoriteShowId = "Show_" + request.getParameter("id");

        Map<String, String> showData = new HashMap<>();
        showData.put("detail", request.getParameter("language"));
        showData.put("id", favoriteShowId);
        showData.put("image", request.getParameter("image"));
        showData.put("name", request.getParameter("name"));
        showData.put("subtitle", request.getParameter("status"));
        showData.put("url", request.getParameter("url"));

        try {
            favoriteService.save(showData, firebaseService.getUser(uid).getEmail(), favoriteShowId);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/favorites";
    }

    // Reference: https://stackoverflow.com/questions/2494774/how-to-explicitly-obtain-post-data-in-spring-mvc
    @PostMapping("/favorites/save/actor")
    public String saveFavoriteActor(HttpServletRequest request, @CookieValue(value = "uid", required = false) String uid) {
        // User is not logged in, need to log in before actor can be saved to favorites
        if (uid == null) {
            return "login";
        }

        // Need to differentiate ID between Actor & Show since TVMaze API doesnt do it for us
        String favoriteActorId = "Actor_" + request.getParameter("id");

        Map<String, String> actorData = new HashMap<>();
        actorData.put("detail", request.getParameter("country"));
        actorData.put("id", favoriteActorId);
        actorData.put("image", request.getParameter("image"));
        actorData.put("name", request.getParameter("name"));
        actorData.put("subtitle", request.getParameter("gender"));
        actorData.put("url", request.getParameter("url"));

        try {
            favoriteService.save(actorData, firebaseService.getUser(uid).getEmail(), favoriteActorId);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/favorites";
    }

    // Need to use POST mapping instead of DELETE mapping since a form calls this and forms do not support DELETE mappings by default.
    @PostMapping("/favorites/delete")
    public String deleteFavorite(@RequestParam(value = "favoriteId") String favoriteId, @CookieValue(value = "uid") String uid) {
        try {
            favoriteService.delete(firebaseService.getUser(uid).getEmail(), favoriteId);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/favorites";
    }

    @PostMapping("/reviews/update")
    public String updateReview(
            @CookieValue(value = "uid") String uid,
            @RequestParam(value = "favoriteId") String favoriteId,
            @RequestParam(value = "newRating") Integer newRating,
            @RequestParam(value = "favoriteName") String favoriteName
    ) {
        // Adding check for newRating to ensure value is between 1-5 in case user manually posts a higher number
        if (newRating > 5) {
            newRating = 5;
        } else if (newRating < 1) {
            newRating = 1;
        }

        try {
            reviewService.save(new Review(uid, favoriteId, newRating, favoriteName));
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/favorites";
    }

    // Need to use POST mapping instead of DELETE mapping since a form calls this and forms do not support DELETE mappings by default.
    @PostMapping("/reviews/delete")
    public String deleteReview(@CookieValue(value = "uid") String uid, @RequestParam(value = "favoriteId") String favoriteId) {
        try {
            reviewService.delete(new ReviewId(uid, favoriteId));
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/favorites";
    }
}
