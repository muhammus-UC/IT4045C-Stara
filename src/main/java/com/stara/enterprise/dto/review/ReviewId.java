package com.stara.enterprise.dto.review;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * ReviewId is used as a composite primary key for Reviews in SQL.
 * Reference: https://www.baeldung.com/jpa-composite-primary-keys
 */
@Embeddable
public @Data
class ReviewId implements Serializable {
    // String of uid of user who Review belongs to
    private String uid;
    // String of id of Favorite which Review belongs to
    private String favoriteId;

    public ReviewId() {}

    public ReviewId(String uid, String favoriteId) {
        this.uid = uid;
        this.favoriteId = favoriteId;
    }
}
