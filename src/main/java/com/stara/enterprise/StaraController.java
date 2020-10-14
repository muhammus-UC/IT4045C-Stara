package com.stara.enterprise;

import com.stara.enterprise.dto.Favorite;
import com.stara.enterprise.dto.ShowFeed;
import com.stara.enterprise.service.IFavoriteService;
import com.stara.enterprise.service.IShowFeedService;
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
     * Returns show results from TVMaze API
     * Equivalent of running https://api.tvmaze.com/search/shows?q=showName
     */

    /**
     * GetMapping for /shows endpoint
     * Equivalent of running https://api.tvmaze.com/search/shows?q=showName
     *
     * @param searchTerm show name to search for REQUIRED
     * @return search results from TVMaze API
     */
    @GetMapping("/shows")
    public ResponseEntity searchShows(@RequestParam(value = "searchTerm", required = true) String searchTerm) {
        try {
            List<ShowFeed> shows = showFeedService.fetchShows(searchTerm);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(shows, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
