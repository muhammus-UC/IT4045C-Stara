package com.stara.enterprise.dao.review;

import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;

import java.util.List;

public interface IReviewDAO {
    void delete(ReviewId reviewId);

    Review fetch(ReviewId reviewId);

    List<Review> fetchReviewsByUid(String uid);

    Review save(Review review) throws Exception;
}
