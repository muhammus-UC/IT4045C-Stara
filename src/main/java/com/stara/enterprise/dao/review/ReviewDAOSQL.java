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

    @Override
    public void delete(ReviewId reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Review fetch(ReviewId reviewId) {
        return reviewRepository.findById(reviewId).get();
    }

    @Override
    public Map<String, Review> fetchReviewsByUid(String uid) {
        List<Review> reviewList = reviewRepository.findByReviewIdUid(uid);

        // Reference: https://www.baeldung.com/java-list-to-map#after-java8
        // Reference: https://stackoverflow.com/a/20887747
        Map<String, Review> reviewMap = reviewList
                .stream()
                .collect(Collectors.toMap(Review::getReviewIdFavoriteId, Function.identity()));

        return reviewMap;
    }

    @Override
    public Review save(Review review) throws Exception {
        return reviewRepository.save(review);
    }
}
