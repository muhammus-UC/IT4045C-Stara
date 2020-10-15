package com.stara.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class Show {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("language")
    private String language;
    @SerializedName("status")
    private String status;
    @SerializedName("image")
    private ImageURLs image;
}
