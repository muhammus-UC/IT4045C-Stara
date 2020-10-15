package com.stara.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class ImageURLs {
    @SerializedName("medium")
    private String medium;
}
