package com.stara.enterprise.service.review;

import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;

import java.util.Map;

public interface IReviewService {
    void delete(ReviewId reviewId);

    Review fetch(ReviewId reviewId);

    Map<String, Review> fetchReviewsByUid(String uid);

    Review save(Review review) throws Exception;
}
