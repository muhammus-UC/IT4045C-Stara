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
        return "Actor Info - ID: " + id +
                ". URL: " + url +
                ". Name: " + name +
                ". Country: " + country +
                ". Gender: " + gender +
                ". Image URL: " + image;
    }

    // Property country can be null, manually define get method.
    public ActorCountry getCountry() {
        if (country == null) {
            country = new ActorCountry();
            country.setName("Country Unknown");
        }
        return country;
    }

    // Property gender can be null, manually define get method.
    public String getGender() {
        if (gender == null) {
            gender = "Gender Unknown";
        }
        return gender;
    }

    // Property image can be null, manually define get method.
    public ImageURLs getImage() {
        if (image == null) {
            image = new ImageURLs();
            // Placeholder indicating no image found
            image.setMedium("images/null.svg");
        }
        return image;
    }
}
