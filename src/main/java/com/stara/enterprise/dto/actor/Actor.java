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
    // ActorCountry object used to parse nested JSON
    @SerializedName("country")
    private ActorCountry country;
    @SerializedName("gender")
    private String gender;
    // ImageURLs object used to parse nested JSON
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

    /**
     * Property country may be null, manually defining get method
     *
     * @return country name if not null, otherwise "Country Unknown"
     */
    public ActorCountry getCountry() {
        if (country == null) {
            country = new ActorCountry();
            country.setName("Country Unknown");
        }
        return country;
    }

    /**
     * Property gender may be null, manually defining get method
     *
     * @return gender if not null, otherwise "Gender Unknown"
     */
    public String getGender() {
        if (gender == null) {
            gender = "Gender Unknown";
        }
        return gender;
    }

    /**
     * Property image may be null, manually define get method
     *
     * @return image if not null, otherwise image with a medium property of "images/null.svg" (a placeholder image to indicate null)
     */
    public ImageURLs getImage() {
        if (image == null) {
            image = new ImageURLs();
            // Placeholder indicating no image found
            image.setImageUrl("images/null.svg");
        }
        return image;
    }
}
