package com.stara.enterprise.dao.review;

import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Profile("!test")
public interface ReviewRepository extends CrudRepository<Review, ReviewId> {
    List<Review> findByReviewIdUid(String uid);
}
