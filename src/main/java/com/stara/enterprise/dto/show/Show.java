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
    // ImageURLs object used to parse nested JSON
    @SerializedName("image")
    private ImageURLs image;

    public String toString() {
        return "Show Info - ID: " + id +
                ". URL: " + url +
                ". Name: " + name +
                ". Language: " + language +
                ". Status: " + status +
                ". Image URL: " + image;
    }

    /**
     * Property language may be null, manually defining get method
     *
     * @return language if not null, otherwise "Language Unknown"
     */
    public String getLanguage() {
        if (language == null) {
            language = "Language Unknown";
        }
        return language;
    }

    /**
     * Property status may be null, manually defining get method
     *
     * @return status if not null, otherwise "Status Unknown"
     */
    public String getStatus() {
        if (status == null) {
            status = "Status Unknown";
        }
        return status;
    }

    /**
     * Property image may be null, manually define get method
     *
     * @return image if not null, otherwise image with a medium property of "images/null.svg" (a placeholder image to indicate null)
     */
    public ImageURLs getImage() {
        if (image == null) {
            image = new ImageURLs();
            image.setMedium("images/null.svg");
        }
        return image;
    }
}
