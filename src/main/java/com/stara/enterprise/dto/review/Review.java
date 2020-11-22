package com.stara.enterprise.dto.review;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

// Reference: https://www.baeldung.com/jpa-composite-primary-keys
@Entity
public @Data
class Review {
    @EmbeddedId
    ReviewId reviewId;
    Integer rating;
    String favoriteName;

    public Review() {}

    public Review(String uid, String favoriteId, Integer rating, String favoriteName) {
        this.setReviewId(uid, favoriteId);
        this.setRating(rating);
        this.setFavoriteName(favoriteName);
    }

    public void setReviewId(ReviewId reviewId) {
        this.reviewId = reviewId;
    }

    public void setReviewId(String uid, String favoriteId) {
        this.reviewId = new ReviewId(uid, favoriteId);
    }
}
