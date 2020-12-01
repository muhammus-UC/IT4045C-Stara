package com.stara.enterprise.dao.review;

import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;

import java.util.Map;

public interface IReviewDAO {
    /**
     * Delete Review with given ReviewId.
     *
     * @param reviewId - ReviewId object used to identify the Review to delete.
     */
    void delete(ReviewId reviewId);

    /**
     * Fetch Review with given ReviewId.
     *
     * @param reviewId - ReviewId object used to identify the Review to fetch.
     * @return Review object corresponding to ReviewId given.
     */
    Review fetch(ReviewId reviewId);

    /**
     * Fetch a Map containing all the Reviews for uid given.
     *
     * @param uid - uid of user to fetch Reviews for.
     * @return Map<String, Review> containing all the Reviews for the uid given.
     */
    Map<String, Review> fetchReviewsByUid(String uid);

    /**
     * Saves Review to database.
     *
     * @param review - Review object to save to database.
     * @return review that was just saved.
     * @throws Exception thrown in case review is unable to be saved for some reason or another.
     */
    Review save(Review review) throws Exception;
}
