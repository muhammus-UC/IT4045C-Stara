package com.stara.enterprise.dto.review;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

// Reference: https://www.baeldung.com/jpa-composite-primary-keys
@Embeddable
public @Data
class ReviewId implements Serializable {
    private String uid;
    private String favoriteId;

    public ReviewId() {}

    public ReviewId(String uid, String favoriteId) {
        this.uid = uid;
        this.favoriteId = favoriteId;
    }
}
