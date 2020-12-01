package com.stara.enterprise.service.review;

import com.stara.enterprise.dao.review.IReviewDAO;
import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    IReviewDAO reviewDAO;

    public ReviewService() {}

    public ReviewService(IReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    /**
     * Delete Review with given ReviewId via DAO.
     *
     * @param reviewId - ReviewId object used to identify the Review to delete.
     */
    @Override
    public void delete(ReviewId reviewId) {
        reviewDAO.delete(reviewId);
    }

    /**
     * Fetch Review with given ReviewId via DAO.
     *
     * @param reviewId - ReviewId object used to identify the Review to fetch.
     * @return Review object corresponding to ReviewId given.
     */
    @Override
    public Review fetch(ReviewId reviewId) {
        return reviewDAO.fetch(reviewId);
    }

    /**
     * Fetch a Map containing all the Reviews for uid given via DAO.
     *
     * @param uid - uid of user to fetch Reviews for.
     * @return Map<String,Review> containing all the Reviews for the uid given.
     */
    @Override
    public Map<String, Review> fetchReviewsByUid(String uid) {
        return reviewDAO.fetchReviewsByUid(uid);
    }

    /**
     * Saves Review via DAO.
     * @param review - Review object to save via DAO.
     * @return review that was just saved.
     * @throws Exception thrown in case review is unable to saved for some reason or another.
     */
    @Override
    public Review save(Review review) throws Exception {
        return reviewDAO.save(review);
    }
}
