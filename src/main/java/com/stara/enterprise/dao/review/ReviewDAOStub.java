package com.stara.enterprise.dao.review;

import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Review> fetchReviewsByUid(String uid) {
        return new ArrayList<Review>();
    }

    @Override
    public Review save(Review review) throws Exception {
        allReviews.put(review.getReviewId(), review);
        return review;
    }
}
