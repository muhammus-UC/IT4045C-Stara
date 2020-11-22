package com.stara.enterprise.dao.review;

import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Review> fetchReviewsByUid(String uid) {
        return reviewRepository.findByReviewIdUid(uid);
    }

    @Override
    public Review save(Review review) throws Exception {
        return reviewRepository.save(review);
    }
}
