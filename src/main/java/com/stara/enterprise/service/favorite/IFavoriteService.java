package com.stara.enterprise.service.favorite;

import com.stara.enterprise.dto.Favorite;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IFavoriteService {
    /**
     * Delete Favorite for email and id specified.
     *
     * @param email of user who to delete Favorite for
     * @param id    of Favorite to delete
     */
    void delete(String email, String id);

    /**
     * Get all Favorites for user by email specified.
     *
     * @param email of user who to retrieve Favorite(s) for
     * @return List<Favorite> containing all favorites that belong to user
     * @throws ExecutionException   when attempting to retrieve the result of a task that aborted by throwing an exception
     *                              Reference: https://developer.android.com/reference/java/util/concurrent/ExecutionException
     * @throws InterruptedException thrown when a thread is interrupted
     *                              Reference: https://www.yegor256.com/2015/10/20/interrupted-exception.html
     */
    List<Favorite> fetchAll(String email) throws ExecutionException, InterruptedException;

    /**
     * Create Favorite for email and id specified.
     *
     * @param favoriteData Map<String, String> object containing the Favorite data to write.
     * @param email        of user who to create Favorite for
     * @param id           of Favorite to create
     */
    void save(Map<String, String> favoriteData, String email, String id);
}
