package com.stara.enterprise;

import com.google.firebase.auth.FirebaseAuthException;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.stara.enterprise.dto.Favorite;
import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.actor.ActorFeedItem;
import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import com.stara.enterprise.dto.show.ShowFeedItem;
import com.stara.enterprise.service.actor.IActorFeedService;
import com.stara.enterprise.service.favorite.IFavoriteService;
import com.stara.enterprise.service.firebase.IFirebaseService;
import com.stara.enterprise.service.review.IReviewService;
import com.stara.enterprise.service.schedule.IScheduleFeedService;
import com.stara.enterprise.service.show.IShowFeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class StaraController {
    final Logger log = LoggerFactory.getLogger(this.getClass());

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
     * GetMapping for / (root) endpoint.
     * Displays schedule of TV premieres for user's country.
     * Equivalent of running https://www.tvmaze.com/schedule.
     *
     * @param countryCode - OPTIONAL, country to display schedule for. If not specified, user's IP address is used to determine location.
     * @param model       - used to pass schedule and other data to UI layer.
     * @param uid         - OPTIONAL, uid of currently logged in user, gotten via cookie. Used by Thymeleaf to determine if login or logout button should be shown in topnav.html fragment.
     * @param request     - HttpServletRequest, automatic parameter given by SpringBoot, used to access user's IP Address.
     * @return HTML page that displays schedule for user's location or countryCode specified.
     */
    @GetMapping("/")
    public String displaySchedule(
            @RequestParam(value = "countryCode", required = false) String countryCode,
            Model model,
            @CookieValue(value = "uid", required = false) String uid,
            HttpServletRequest request
    ) {
        log.debug("Entering / (root) endpoint.");
        log.trace("User's uid is: " + uid);

        String countryName = "Country Not Named";

        // User did not specify a country code, need to determine it via IP Address.
        if (countryCode == null) {
            log.trace("User did not specify a country code.");
            String ipAddress = request.getRemoteAddr();
            String dbLocation = "assets/GeoLite2-Country.mmdb";

            // User is on localhost. Need to get IP Address via third party URL.
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                try {
                    log.trace("User is on localhost.");
                    URL urlIpLookup = new URL("https://ip.derp.uk/");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlIpLookup.openStream()));
                    ipAddress = bufferedReader.readLine().trim();
                    bufferedReader.close();
                } catch (MalformedURLException e) {
                    log.error("Unable to use third party URL to get IP Address, a MalformedURLException occurred. Message: " + e.getMessage(), e);
                    return "error";
                } catch (IOException e) {
                    log.error("Unable to read third party site to get IP Address, an IOException occurred. Message: " + e.getMessage(), e);
                    return "error";
                }
            }

            log.info("User's IP Address is: " + ipAddress);

            try {
                // Read MaxMind GeoIP2 Database, lets us convert an IP Address into a Location.
                File ipDbFile = new File(dbLocation);
                DatabaseReader ipDbReader = new DatabaseReader.Builder(ipDbFile).build();

                // Parse IP address into something MaxMind can use.
                InetAddress inetAddress = InetAddress.getByName(ipAddress);
                // For IP address provided, store country related data from MaxMind database.
                CountryResponse response = ipDbReader.country(inetAddress);

                countryCode = response.getCountry().getIsoCode();
                countryName = response.getCountry().getName();
            } catch (UnknownHostException e) {
                log.error("Unable to parse IP Address/Host provided, an UnknownHostException occurred. Message: " + e.getMessage(), e);
                return "error";
            } catch (IOException e) {
                log.error("Unable to parse Database File, an IOException occurred. Message: " + e.getMessage(), e);
                return "error";
            } catch (GeoIp2Exception e) {
                log.error("Unable to parse IP Address/Host against MaxMind GeoIP2 Database, a GeoIP2Exception occurred. Message: " + e.getMessage(), e);
                return "error";
            }
        }
        // User provided country code, use it to determine country name.
        else {
            log.info("User specified country code. It is " + countryCode);
            Locale locale = new Locale("", countryCode);
            countryName = locale.getDisplayCountry();
        }

        List<ScheduleFeedItem> scheduleFeed;
        try {
            log.trace("Attempting to fetch schedule for country " + countryCode);
            scheduleFeed = scheduleFeedService.fetchScheduleFeed(countryCode);
            log.info("Fetched schedule for country " + countryCode);
        } catch (IOException e) {
            log.error("Unable to fetch schedule, an IOException occurred. Message: " + e.getMessage(), e);
            return "error";
        }

        if (scheduleFeed == null) {
            log.warn("Schedule Feed is Empty!");
        }

        model.addAttribute("scheduleFeed", scheduleFeed);
        model.addAttribute("countryName", countryName);
        model.addAttribute("uid", uid);
        return "schedule";
    }

    /**
     * GetMapping for /search endpoint.
     * Searches TV Maze API for Actors & Shows by search term provided.
     * Equivalent of running https://www.tvmaze.com/search?q=searchTerm.
     * TVMaze API does not natively support searching for both shows and actors in one call so have to make two separate calls.
     *
     * @param searchTerm - Show or Actor to search for.
     * @param model      - used to pass list of Shows and Actors to UI layer.
     * @param uid        - OPTIONAL, uid of currently logged in user, gotten via cookie. Used by Thymeleaf to determine if login or logout button should be shown in topnav.html fragment.
     * @return HTML page that displays search results.
     */
    @GetMapping("/search")
    public String searchShowsAndActors(
            @RequestParam(value = "searchTerm") String searchTerm,
            Model model,
            @CookieValue(value = "uid", required = false) String uid
    ) {
        log.debug("Entering /search endpoint.");
        log.trace("User's uid is: " + uid);

        try {
            List<ShowFeedItem> shows = showFeedService.fetchShowFeed(searchTerm);
            List<ActorFeedItem> actors = actorFeedService.fetchActorFeed(searchTerm);
            model.addAttribute("searchTerm", searchTerm);
            model.addAttribute("showFeed", shows);
            model.addAttribute("actorFeed", actors);
            model.addAttribute("uid", uid);
            log.info("Fetched shows and actors for search term: " + searchTerm);
            return "search";
        } catch (IOException e) {
            log.error("Unable to fetch either shows or actors, an IOException occurred. Message: " + e.getMessage(), e);
            return "error";
        }
    }

    /**
     * GetMapping for /set-uid endpoint.
     * Used to set cookie that stores user's uid.
     * uid is gotten via Firebase UI as defined in login.html.
     * Reference: https://dzone.com/articles/how-to-use-cookies-in-spring-boot.
     *
     * @param response - HttpServletResponse, automatic parameter given by SpringBoot, used to set cookie.
     * @param uid      - uid of user who just logged in, to be stored in a cookie.
     * @return HTML page that displays favorites for uid specified by redirecting to /favorites endpoint.
     */
    @GetMapping("/set-uid")
    public String setCookie(HttpServletResponse response, @RequestParam(value = "uid") String uid) {
        log.debug("Entering /set-uid endpoint.");
        log.trace("User's uid is: " + uid);

        Cookie cookie = new Cookie("uid", uid);
        cookie.setPath("/");
        response.addCookie(cookie);

        log.info("Logged in user, by setting uid cookie for uid " + uid);
        return "redirect:/favorites";
    }

    /**
     * GetMapping for /unset-uid endpoint.
     * Used to unset cookie that stores user's uid.
     * Reference: https://attacomsian.com/blog/cookies-spring-boot#deleting-cookie.
     *
     * @param response - HttpServletResponse, automatic parameter given by SpringBoot, used to unset cookie.
     * @return HTML page that displays schedule by redirecting to / (root) endpoint.
     */
    @GetMapping("/unset-uid")
    public String unsetCookie(HttpServletResponse response) {
        log.debug("Entering /unset-uid endpoint.");

        // Create cookie of same name of "uid" at same path with age of 0 to remove it.
        Cookie cookie = new Cookie("uid", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        log.info("Logged out user, by unsetting uid cookie.");
        return "redirect:/";
    }

    /**
     * GetMapping for /favorites endpoint.
     * Displays Favorites from Firebase Firestore and Reviews from SQL Database for logged in user.
     *
     * @param uid   - uid of currently logged in user, gotten via cookie.
     *              Used by Thymeleaf to determine if login or logout button should be shown in topnav.html fragment.
     *              Used to determine which user's favorites to display via Firebase.
     * @param model - used to pass favorite and other data to UI layer.
     * @return HTML page that displays favorites for uid specified.
     */
    @GetMapping("/favorites")
    public String fetchAllFavorites(@CookieValue(value = "uid", required = false) String uid, Model model) {
        log.debug("Entering /favorites endpoint.");
        log.trace("User's uid is: " + uid);

        // User is not logged in, can't display favorites. Display login page.
        if (uid == null) {
            log.warn("No UID cookie found. User is not logged in. Redirecting to login...");
            return "login";
        }

        log.info("User is logged in. Fetching favorites and reviews for logged in user with uid " + uid);

        List<Favorite> favorites;
        String displayName;

        try {
            favorites = favoriteService.fetchAll(firebaseService.getUser(uid).getEmail());
            displayName = firebaseService.getUser(uid).getDisplayName();
            log.info("Successfully retrieved favorites from Firebase for uid " + uid + " and displayName " + displayName);
        } catch (InterruptedException e) {
            log.error("Unable to fetch user record from Firebase, an InterruptedException occurred. Message: " + e.getMessage(), e);
            return "error";
        } catch (ExecutionException e) {
            log.error("Unable to fetch user record from Firebase, an ExecutionException occurred. Message: " + e.getMessage(), e);
            return "error";
        } catch (FirebaseAuthException e) {
            log.error("Unable to fetch user record from Firebase, a FirebaseAuthException occurred. Message: " + e.getMessage(), e);
            return "error";
        }

        Map<String, Review> allReviewsForUid = reviewService.fetchReviewsByUid(uid);
        log.info("Successfully retrieved reviews for logged in user with uid " + uid);

        model.addAttribute("displayName", displayName);
        model.addAttribute("favorites", favorites);
        model.addAttribute("uid", uid);
        model.addAttribute("reviews", allReviewsForUid);
        log.info("Displaying favorites and reviews for logged in user with uid " + uid);

        return "favorites";
    }

    /**
     * GetMapping for /favorites/json endpoint.
     * Displays Favorites from Firebase Firestore for uid provided in JSON format.
     * Made to be used by another group from class theoretically.
     *
     * @param uid - uid of user to fetch Favorites for.
     *            A default value is provided to speed up testing (This likely would not be done in a proper production environment).
     * @return one of the following HTTP status codes & JSON response bodies:
     * Code 200 - OK - Body includes List of Favorites from Firebase for uid specified.
     * Code 204 - No Content - Request completed successfully but no favorites to show so body is empty.
     * Code 500 - Internal Server Error - An error happened, return empty body on purpose instead of providing error information so nefarious users can't use it to exploit endpoint.
     */
    @GetMapping(value = "/favorites/json", produces = "application/json")
    public ResponseEntity<List<Favorite>> fetchAllFavoritesJSON(@RequestParam(value = "uid", defaultValue = "lI9eajMz4qOZvEaL2SQ7ielz71H3") String uid) {
        log.debug("JSON - Entering /favorites/json endpoint.");
        log.info("JSON - UID to fetch favorites for in JSON is: " + uid);

        List<Favorite> favorites;
        try {
            favorites = favoriteService.fetchAll(firebaseService.getUser(uid).getEmail());
            log.info("JSON - Successfully retrieved favorites from Firebase for uid " + uid);
        } catch (InterruptedException e) {
            log.error("JSON - Unable to fetch user record from Firebase, an InterruptedException occurred. Message: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            log.error("JSON - Unable to fetch user record from Firebase, an ExecutionException occurred. Message: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FirebaseAuthException e) {
            log.error("JSON - Unable to fetch user record from Firebase, a FirebaseAuthException occurred. Message: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // No Favorites found for uid specified, send empty body with code 204
        if (favorites.isEmpty()) {
            log.warn("JSON - No favorites found for user with uid: " + uid);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(favorites, headers, HttpStatus.OK);
        }
    }

    /**
     * PostMapping for /favorites/save/show endpoint
     * Saves the specified Show as a Favorite to Firebase Firestore.
     * Reference: https://stackoverflow.com/a/2494817
     *
     * @param request - HttpServletRequest, automatic parameter given by SpringBoot, used to access favorite data that was posted.
     * @param uid     - uid of currently logged in user, gotten via cookie.
     *                Used to determine which user to save favorite for via Firebase.
     * @return HTML page that displays recently changed favorites for logged in user by redirecting to /favorites endpoint.
     */
    @PostMapping("/favorites/save/show")
    public String saveFavoriteShow(HttpServletRequest request, @CookieValue(value = "uid", required = false) String uid) {
        log.debug("Entering /favorites/save/show endpoint.");
        log.trace("User's uid is: " + uid);

        // User is not logged in, need to log in before show can be saved to favorites.
        if (uid == null) {
            log.warn("No UID cookie found. User is not logged in. Redirecting to login...");
            return "login";
        }
        log.info("User is logged in. Saving favorite for logged in user with uid " + uid);

        // Need to manually differentiate ID between Actor & Show since TVMaze API doesn't do it for us.
        String favoriteShowId = "Show_" + request.getParameter("id");

        // Converting Show object properties to Favorite object to later save to Firebase
        Favorite favoriteShow = new Favorite();
        favoriteShow.setDetail(request.getParameter("language"));
        favoriteShow.setId(favoriteShowId);
        favoriteShow.setImage(request.getParameter("image"));
        favoriteShow.setName(request.getParameter("name"));
        favoriteShow.setSubtitle(request.getParameter("status"));
        favoriteShow.setUrl(request.getParameter("url"));

        try {
            favoriteService.save(favoriteShow, firebaseService.getUser(uid).getEmail(), favoriteShowId);
            log.info("Saved Favorite Show " + favoriteShowId + " for user with uid " + uid);
        } catch (FirebaseAuthException e) {
            log.error("Unable to fetch user record from Firebase, a FirebaseAuthException occurred. Message: " + e.getMessage(), e);
            return "error";
        }

        return "redirect:/favorites";
    }

    /**
     * PostMapping for /favorites/save/actor endpoint.
     * Saves the specified Actor as a Favorite to Firebase Firestore.
     * Reference: https://stackoverflow.com/a/2494817.
     *
     * @param request - HttpServletRequest, automatic parameter given by SpringBoot, used to access favorite data that was posted.
     * @param uid     - uid of currently logged in user, gotten via cookie.
     *                Used to determine which user to save favorite for via Firebase.
     * @return HTML page that displays recently changed favorites for logged in user by redirecting to /favorites endpoint.
     */
    @PostMapping("/favorites/save/actor")
    public String saveFavoriteActor(HttpServletRequest request, @CookieValue(value = "uid", required = false) String uid) {
        log.debug("Entering /favorites/save/actor endpoint.");
        log.trace("User's uid is: " + uid);

        // User is not logged in, need to log in before actor can be saved to favorites.
        if (uid == null) {
            log.warn("No UID cookie found. User is not logged in. Redirecting to login...");
            return "login";
        }

        // Need to differentiate ID between Actor & Show since TVMaze API doesnt do it for us.
        String favoriteActorId = "Actor_" + request.getParameter("id");

        // Converting Actor object properties to Favorite object to later save to Firebase
        Favorite favoriteActor = new Favorite();
        favoriteActor.setDetail(request.getParameter("country"));
        favoriteActor.setId(favoriteActorId);
        favoriteActor.setImage(request.getParameter("image"));
        favoriteActor.setName(request.getParameter("name"));
        favoriteActor.setSubtitle(request.getParameter("gender"));
        favoriteActor.setUrl(request.getParameter("url"));

        try {
            favoriteService.save(favoriteActor, firebaseService.getUser(uid).getEmail(), favoriteActorId);
            log.info("Saved Favorite Actor " + favoriteActorId + " for user with uid " + uid);
        } catch (FirebaseAuthException e) {
            log.error("Unable to fetch user record from Firebase, a FirebaseAuthException occurred. Message: " + e.getMessage(), e);
            return "error";
        }

        return "redirect:/favorites";
    }

    /**
     * PostMapping for /favorites/delete endpoint.
     * Deletes the specified Favorite from Firebase Firestore for logged in user.
     * Need to use POST mapping instead of DELETE mapping since a form calls this, and forms do not support DELETE mappings.
     *
     * @param favoriteId - Id of Favorite to delete.
     * @param uid        - uid of currently logged in user, gotten via cookie.
     *                   Used to determine which user to remove favorite for via Firebase.
     * @return HTML page that displays recently changed favorites for logged in user by redirecting to /favorites endpoint.
     */
    @PostMapping("/favorites/delete")
    public String deleteFavorite(@RequestParam(value = "favoriteId") String favoriteId, @CookieValue(value = "uid") String uid) {
        log.debug("Entering /favorites/delete endpoint.");
        log.trace("User's uid is: " + uid);

        try {
            favoriteService.delete(firebaseService.getUser(uid).getEmail(), favoriteId);
            log.info("Deleted Favorite " + favoriteId + " for user with uid " + uid);
        } catch (FirebaseAuthException e) {
            log.error("Unable to fetch user record from Firebase, a FirebaseAuthException occurred. Message: " + e.getMessage(), e);
            return "error";
        }

        return "redirect:/favorites";
    }

    /**
     * GetMapping for /reviews endpoint.
     * Displays Reviews from SQL Database for uid provided in JSON format.
     * Made to be used by another group from class theoretically.
     *
     * @param uid - uid of user to fetch Reviews for.
     *            A default value is provided to speed up testing (This likely would not be done in a proper production environment).
     * @return one of the following HTTP status codes & JSON response bodies
     * Code 200 - OK - Body includes List of Reviews from SQL Database for uid specified.
     * Code 204 - No Content - Request completed successfully but no reviews to show so body is empty.
     * Code 500 - Internal Server Error - An error happened, return empty body on purpose instead of providing error information so nefarious users can't use it to exploit endpoint.
     */
    @GetMapping(value = "/reviews", produces = "application/json")
    public ResponseEntity<Map<String, Review>> fetchAllReviewsForUID(@RequestParam(value = "uid", defaultValue = "lI9eajMz4qOZvEaL2SQ7ielz71H3") String uid) {
        log.debug("JSON - Entering /reviews endpoint.");
        log.info("JSON - UID to fetch reviews for in JSON is: " + uid);

        Map<String, Review> allReviewsForUid = reviewService.fetchReviewsByUid(uid);
        log.info("Successfully retrieved reviews for user with uid " + uid);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // No Reviews found for uid specified, send empty body with code 204
        if (allReviewsForUid.isEmpty()) {
            log.warn("JSON - No reviews found for user with uid " + uid);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(allReviewsForUid, headers, HttpStatus.OK);
        }
    }

    /**
     * PostMapping for /reviews/update endpoint.
     * Creates/Updates a Review for the specified Favorite in SQL database.
     *
     * @param uid          - uid of currently logged in user, gotten via cookie.
     *                     Used to determine which user to create/update Review to Favorite for via SQL.
     * @param favoriteId   - Id of Favorite to create/update Review for.
     * @param newRating    - Integer between 1-5, representing how much user likes Favorite.
     * @param favoriteName - Name of Favorite to create/update Review for.
     * @return HTML page that displays Favorites and recently changed Reviews by redirecting to /favorites endpoint.
     */
    @PostMapping("/reviews/update")
    public String updateReview(
            @CookieValue(value = "uid") String uid,
            @RequestParam(value = "favoriteId") String favoriteId,
            @RequestParam(value = "newRating") Integer newRating,
            @RequestParam(value = "favoriteName") String favoriteName
    ) {
        log.debug("Enter /reviews/update endpoint.");
        log.trace("User's uid is: " + uid);

        // Adding check for newRating to ensure value is between 1-5 in case user manually posts a higher number or user forgets to enter a number
        if (newRating == null) {
            log.warn("User failed to provide a rating number. Setting it to 3.");
            newRating = 3;
        } else if (newRating > 5) {
            log.warn("User entered a rating higher than 5. Setting it to 5.");
            newRating = 5;
        } else if (newRating < 1) {
            log.warn("User entered a rating lower than 1. Setting it to 1.");
            newRating = 1;
        }

        try {
            reviewService.save(new Review(uid, favoriteId, newRating, favoriteName));
            log.info("Successfully saved review for Favorite " + favoriteName + " with favoriteId of " + favoriteId);
        } catch (Exception e) {
            log.error("Unable to save review, an Exception occurred. Message: " + e.getMessage(), e);
            return "error";
        }

        return "redirect:/favorites";
    }

    /**
     * PostMapping for /reviews/delete endpoint.
     * Deletes the specified Review from SQL database for logged in user.
     * Need to use POST mapping instead of DELETE mapping since a form calls this, and forms do not support DELETE mappings.
     *
     * @param uid        - uid of currently logged in user, gotten via cookie.
     *                   Used to determine which user to delete Review to Favorite for via SQL.
     * @param favoriteId - Id of Favorite to delete Review for.
     * @return HTML page that displays Favorites and recently changed Reviews by redirecting to /favorites endpoint.
     */
    @PostMapping("/reviews/delete")
    public String deleteReview(@CookieValue(value = "uid") String uid, @RequestParam(value = "favoriteId") String favoriteId) {
        log.debug("Entering /reviews/delete endpoint.");
        log.trace("User's uid is: " + uid);

        ReviewId reviewId = new ReviewId(uid, favoriteId);

        reviewService.delete(reviewId);
        log.info("Deleted review with a reviewId of " + reviewId);

        return "redirect:/favorites";
    }
}
