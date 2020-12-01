package com.stara.enterprise.dao.review;

import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Profile("test")
public class ReviewDAOStub implements IReviewDAO {
    // Map used to save Reviews to since this is a stub implementation, meaning there is no real persistence.
    Map<ReviewId, Review> allReviews = new HashMap<>();

    /**
     * Delete Review with given ReviewId from allReviews Map.
     *
     * @param reviewId - ReviewId object used to identify the Review to delete.
     */
    @Override
    public void delete(ReviewId reviewId) {
        allReviews.remove(reviewId);
    }

    /**
     * Fetch Review with given ReviewId from allReviews Map.
     *
     * @param reviewId - ReviewId object used to identify the Review to fetch.
     * @return Review object corresponding to ReviewId given.
     */
    @Override
    public Review fetch(ReviewId reviewId) {
        return allReviews.get(reviewId);
    }

    /**
     * Fetch a Map containing all the Reviews for uid given.
     *
     * @param uid - uid of user to fetch Reviews for.
     * @return Map<String, Review> containing all the Reviews for the uid given.
     */
    @Override
    public Map<String, Review> fetchReviewsByUid(String uid) {

        /*
         * Using optimized way introduced in Java 8 to convert a Map<ReviewId, Review> to Map<String, Review>.
         * The String key in the map corresponds to the FavoriteId of the Review in question.
         * The FavoriteId tells us which Favorite the Review in question belongs to.
         * The Review DTO's getReviewIdFavoriteId() is used to get the FavoriteId and store that as the key for new map.
         *
         * To learn more about how this "optimized" way works read https://www.techiedelight.com/transform-hashmap-java-8/.
         */
        Map<String, Review> reviewMap = allReviews
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getFavoriteId(),
                        entry -> entry.getValue())
                );

        return reviewMap;
    }

    /**
     * Saves Review to allReviews Map.
     *
     * @param review - Review object to save to database.
     * @return review that was just saved
     * @throws Exception thrown in case review is unable to be saved for some reason or another.
     */
    @Override
    public Review save(Review review) throws Exception {
        allReviews.put(review.getReviewId(), review);
        return review;
    }
}
