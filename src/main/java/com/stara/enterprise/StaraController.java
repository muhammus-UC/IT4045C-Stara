package com.stara.enterprise;

import com.stara.enterprise.dto.Favorite;
import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.actor.ActorFeedItem;
import com.stara.enterprise.dto.show.ShowFeedItem;
import com.stara.enterprise.service.favorite.IFavoriteService;
import com.stara.enterprise.service.firebase.FirebaseService;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    FirebaseService firebaseService;

    /**
     * GetMapping for / (root) endpoint
     * Equivalent of running https://www.tvmaze.com/schedule
     *
     * @param countryCode country to display schedule for
     * @param model used to pass schedule to UI layer
     * @return HTML page that displays schedule in an organized manner
     */
    @GetMapping("/")
    public String displaySchedule(@RequestParam(value = "countryCode", required = false) String countryCode, Model model) {
        try {
            if (countryCode == null) { countryCode = "US"; }
            List<ScheduleFeedItem> scheduleFeed = scheduleFeedService.fetchScheduleFeed(countryCode);
            model.addAttribute("scheduleFeed", scheduleFeed);
            return "schedule";
        } catch (IOException e) {
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
    public ResponseEntity searchShows(@RequestParam(value = "searchShow", required = true) String searchShow) {
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
    public String searchShowsAndActors(@RequestParam(value = "searchTerm", required = true) String searchTerm, Model model) {
        try {
            List<ShowFeedItem> shows = showFeedService.fetchShowFeed(searchTerm);
            List<ActorFeedItem> actors = actorFeedService.fetchActorFeed(searchTerm);
            model.addAttribute("showFeed", shows);
            model.addAttribute("actorFeed", actors);
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

    @GetMapping("/favorites")
    public String fetchAllFavorites(@CookieValue(value = "uid", required = false) String uid, Model model) {
        try {
            if (uid == null) {
                System.out.println("No UID cookie found. User is not logged in.");
                return "login";
            }

            System.out.println("User is logged in. Fetching favorites.");
            List<Favorite> favorites = favoriteService.fetchAll(firebaseService.getUser(uid).getEmail());
            model.addAttribute("favorites", favorites);
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
        }

        return "redirect:/favorites";
    }
}
