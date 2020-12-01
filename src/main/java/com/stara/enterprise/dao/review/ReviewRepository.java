package com.stara.enterprise.dao.review;

import com.stara.enterprise.dto.review.Review;
import com.stara.enterprise.dto.review.ReviewId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// Extends CrudRepository to easily be able to communicate with SQL
@Profile("!test")
public interface ReviewRepository extends CrudRepository<Review, ReviewId> {
    /**
     * Find Reviews that belong to uid given.
     * Equivalent of SQL Statement: SELECT * FROM `stara`.`review` WHERE uid='d41d8cd98f00b204e9800998ecf8';
     *
     * @param uid - uid of user to get Reviews for
     * @return List<Review> containing all the Reviews belonging to uid specified.
     */
    List<Review> findByReviewIdUid(String uid);
}
