package com.stara.enterprise.dao.review;

import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Profile("dev")
public class ReviewDAOSQL implements IReviewDAO {
    @Autowired
    ReviewRepository reviewRepository;

    /**
     * Delete Review with given ReviewId from SQL Database via ReviewRepository.
     *
     * @param reviewId - ReviewId object used to identify the Review to delete.
     */
    @Override
    public void delete(ReviewId reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    /**
     * Fetch Review with given ReviewId from SQL Database via ReviewRepository.
     *
     * @param reviewId - ReviewId object used to identify the Review to fetch.
     * @return Review object corresponding to ReviewId given.
     */
    @Override
    public Review fetch(ReviewId reviewId) {
        return reviewRepository.findById(reviewId).get();
    }

    /**
     * Fetch a Map containing all the Reviews for uid given via ReviewRepository.
     *
     * @param uid - uid of user to fetch Reviews for.
     * @return Map<String,Review> containing all the Reviews for the uid given.
     */
    @Override
    public Map<String, Review> fetchReviewsByUid(String uid) {
        List<Review> reviewList = reviewRepository.findByReviewIdUid(uid);

        /*
         * Using optimized way introduced in Java 8 to convert a List<Review> to a Map<String, Review>.
         * The String key in the map corresponds to the FavoriteId of the Review in question.
         * The FavoriteId tells us which Favorite the Review in question belongs to.
         * The Review DTO's getReviewIdFavoriteId() is used to get the FavoriteId and store that as the key.
         *
         * To learn more about how this "optimized" way works read the references below.
         * Reference: https://www.baeldung.com/java-list-to-map#after-java8
         * Reference: https://stackoverflow.com/a/20887747
         */
        Map<String, Review> reviewMap = reviewList
                .stream()
                .collect(Collectors.toMap(Review::getReviewIdFavoriteId, Function.identity()));

        return reviewMap;
    }

    /**
     * Saves Review to SQL Database via ReviewRepository.
     *
     * @param review - Review object to save to SQL Database.
     * @return review that was just saved.
     * @throws Exception thrown in case ReviewRepository is unable to save review for some reason or another.
     */
    @Override
    public Review save(Review review) throws Exception {
        return reviewRepository.save(review);
    }
}
