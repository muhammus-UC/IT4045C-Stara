package com.stara.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * DTO to access nested image URLs.
 */
public @Data
class ImageURLs {
    @SerializedName("medium")
    private String medium;
}
