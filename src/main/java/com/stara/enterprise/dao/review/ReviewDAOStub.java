package com.stara.enterprise.dao.review;

import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Profile("test")
public class ReviewDAOStub implements IReviewDAO {
    Map<ReviewId, Review> allReviews = new HashMap<>();

    @Override
    public void delete(ReviewId reviewId) {
        allReviews.remove(reviewId);
    }

    @Override
    public Review fetch(ReviewId reviewId) {
        return allReviews.get(reviewId);
    }

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

    @Override
    public Review save(Review review) throws Exception {
        allReviews.put(review.getReviewId(), review);
        return review;
    }
}
