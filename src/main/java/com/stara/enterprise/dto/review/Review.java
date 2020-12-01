package com.stara.enterprise.dto.review;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * A Review is a rating left by a user of a Favorite.
 * A Review can NOT exist without a corresponding Favorite but a Favorite can exist without a Review.
 *
 * Uses Composite Primary Key
 * Reference: https://www.baeldung.com/jpa-composite-primary-keys
 */
@Entity
public @Data
class Review {
    // Composite Primary Key made up of uid of user & id of Favorite that Review belongs to.
    @EmbeddedId
    ReviewId reviewId;
    // Int intended to be between 1-5 representing how much the user likes the Favorite.
    Integer rating;
    // String of name of Favorite Review belongs to. This could technically be removed but is used to make manually reading of the SQL data easier.
    String favoriteName;

    public Review() {
    }

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

    /**
     * Primarily used to quickly convert a List<Review> to a Map<String, Review>
     *
     * @return the id of the Favorite the Review belongs to
     * @see com.stara.enterprise.dao.review.IReviewDAO fetchReviewsByUid()
     */
    public String getReviewIdFavoriteId() {
        return this.reviewId.getFavoriteId();
    }
}
