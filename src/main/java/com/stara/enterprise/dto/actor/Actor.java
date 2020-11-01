package com.stara.enterprise.dto.actor;

import com.google.gson.annotations.SerializedName;
import com.stara.enterprise.dto.ImageURLs;
import lombok.Data;

public @Data
class Actor {
    @SerializedName("id")
    private int id;
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private ActorCountry country;
    @SerializedName("gender")
    private String gender;
    @SerializedName("image")
    private ImageURLs image;

    public String toString() {
        // Handle possible null for ActorCountry
        String countryName = null;
        if (country != null) {
            countryName = country.toString();
        }

        return "Actor Info - ID: " + id +
                ". URL: " + url +
                ". Name: " + name +
                ". Country: " + countryName +
                ". Gender: " + gender +
                ". Image URL: " + image.getMedium();
    }
}
