package com.stara.enterprise;

import com.stara.enterprise.dto.Favorite;
import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.actor.ActorFeedItem;
import com.stara.enterprise.dto.show.ShowFeedItem;
import com.stara.enterprise.service.IFavoriteService;
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

import java.io.IOException;
import java.util.List;

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

    /**
     * RequestMapping for root (/) endpoint
     * Create a Stara favorite object then display start
     * @return Stara start page
     */
    @RequestMapping("/")
    public String index(Model model) {
        // Create and define favorite object
        Favorite favorite = new Favorite();
        favorite.setId(318);
        favorite.setName("Community");
        favorite.setSubtitle("English");
        favorite.setDetail("Ended");

        // Save newly created favorite to list of all favorites
        try {
            favoriteService.save(favorite);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add newly created favorite to model so it can be displayed
        model.addAttribute(favorite);

        return "start";
    }

    /**
     * RequestMapping for /saveFavorite endpoint
     * Save a new favorite with details provided via HTTP query string
     * @param favorite provided through HTTP query
     * @return Stara start page displaying newly saved favorite
     */
    @RequestMapping("/saveFavorite")
    public String saveFavorite(Favorite favorite) {
        try {
            favoriteService.save(favorite);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "start";
    }

    /**
     * GetMapping for /favorite endpoint
     * @return all favorites
     */
    @GetMapping("/favorite")
    @ResponseBody
    public List<Favorite> fetchAllFavorites() {
        return favoriteService.fetchAll();
    }

    /**
     * PostMapping for /favorite endpoint
     * Create a new favorite with details provided via JSON
     *
     * Returns one of the following status codes:
     * 201: Favorite creation successful
     * 409: Favorite creation failed
     *
     * @param favorite a JSON representation of a Favorite object to create
     * @return the newly created favorite
     */
    @PostMapping(value = "/favorite", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Favorite createFavorite(@RequestBody Favorite favorite) {
        Favorite newFavorite = null;
        try {
            newFavorite = favoriteService.save(favorite);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFavorite;
    }

    /**
     * GetMapping for /favorite/{id} endpoint
     *
     * Returns one of the following status codes:
     * 200: Favorite Found
     * 400: Favorite NOT found
     *
     * @param id a unique identifier for favorite to fetch
     */
    @GetMapping("/favorite/{id}")
    public ResponseEntity fetchFavoriteByID(@PathVariable("id") String id) {
        try {
            Favorite foundFavorite = favoriteService.fetchById(Integer.parseInt(id));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(foundFavorite, headers, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DeleteMapping for /favorite/{id} endpoint
     * Delete favorite with provided ID.
     *
     * @param id a unique identifier for favorite to delete
     *
     * @return one of the following status codes:
     *      200: Favorite deletion success, even if favorite didn't exist
     *      409: Favorite deletion error, likely provided malformed id
     */
    @DeleteMapping("/favorite/{id}")
    public ResponseEntity deleteFavorite(@PathVariable("id") String id) {
        try {
            favoriteService.delete(Integer.parseInt(id));
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
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
}
