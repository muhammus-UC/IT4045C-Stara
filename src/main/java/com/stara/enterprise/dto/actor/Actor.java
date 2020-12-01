package com.stara.enterprise.dto.actor;

import com.google.gson.annotations.SerializedName;
import com.stara.enterprise.dto.ImageURLs;
import lombok.Data;

public @Data
class Actor {
    // Int used to uniquely identify Actor from other Actors on TVMaze Database. Note: This doesn't uniquely identify Actor from Shows.
    @SerializedName("id")
    private int id;
    // String of URL to page for Actor on TVMaze.
    @SerializedName("url")
    private String url;
    // String of name of Actor.
    @SerializedName("name")
    private String name;
    // ActorCountry object used to parse nested JSON containing information about country Actor was born in.
    @SerializedName("country")
    private ActorCountry country;
    // String of gender of Actor.
    @SerializedName("gender")
    private String gender;
    // ImageURLs object used to parse nested JSON containing URLs to images of Actor.
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
     * Property country may be null when retrieved from TVMaze API, manually defining get method
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
     * Property gender may be null when retrieved from TVMaze API, manually defining get method
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
     * Property image may be null when retrieved from TVMaze API, manually define get method
     *
     * @return image if not null, otherwise image with a medium property of "images/null.svg" (a placeholder image to indicate null)
     */
    public ImageURLs getImage() {
        if (image == null) {
            image = new ImageURLs();
            // Placeholder indicating no image found
            image.setMedium("images/null.svg");
        }
        return image;
    }
}
