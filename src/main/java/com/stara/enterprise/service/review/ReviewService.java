package com.stara.enterprise.service.review;

import com.stara.enterprise.dao.review.IReviewDAO;
import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReviewService implements IReviewService {
    @Autowired
    IReviewDAO reviewDAO;

    public ReviewService() {}

    public ReviewService(IReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    @Override
    public void delete(ReviewId reviewId) {
        reviewDAO.delete(reviewId);
    }

    @Override
    public Review fetch(ReviewId reviewId) {
        return reviewDAO.fetch(reviewId);
    }

    @Override
    public List<Review> fetchReviewsByUid(String uid) {
        return reviewDAO.fetchReviewsByUid(uid);
    }

    @Override
    public Review save(Review review) throws Exception {
        return reviewDAO.save(review);
    }
}
