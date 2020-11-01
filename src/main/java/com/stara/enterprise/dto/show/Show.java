package com.stara.enterprise.dto.show;

import com.google.gson.annotations.SerializedName;
import com.stara.enterprise.dto.ImageURLs;
import lombok.Data;

public @Data
class Show {
    @SerializedName("id")
    private int id;
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;
    @SerializedName("language")
    private String language;
    @SerializedName("status")
    private String status;
    @SerializedName("image")
    private ImageURLs image;

    public String toString() {
        return "Show Info - ID: " + id +
                ". URL: " + url +
                ". Name: " + name +
                ". Language: " + language +
                ". Status: " + status +
                ". Image URL: " + image.getMedium();
    }
}
