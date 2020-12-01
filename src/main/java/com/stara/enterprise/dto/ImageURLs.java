package com.stara.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * DTO to access nested image URLs in nested JSON from TVMaze API.
 * String medium holds the URL for the medium-sized image.
 * Other sizes are available from TVMaze API but don't need them for our use case.
 */
public @Data
class ImageURLs {
    @SerializedName("medium")
    private String medium;

    public ImageURLs() {}

    public ImageURLs(String medium) {
        this.medium = medium;
    }
}
