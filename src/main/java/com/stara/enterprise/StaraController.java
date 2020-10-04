package com.stara.enterprise;

import com.stara.enterprise.dto.Favorite;
import com.stara.enterprise.service.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StaraController {
    @Autowired
    IFavoriteService favoriteService;

    /**
     * Handle the root (/) endpoint
     * @return a basic start page
     */
    @RequestMapping("/")
    public String index() {
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
        Favorite foundFavorite = favoriteService.fetchById(Integer.parseInt(id));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(foundFavorite, headers, HttpStatus.OK);
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
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
