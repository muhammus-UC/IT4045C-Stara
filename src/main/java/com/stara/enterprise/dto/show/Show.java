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
                ". Image URL: " + image;
    }

    // Property language can be null, manually define get method.
    public String getLanguage() {
        if (language == null) {
            language = "Language Unknown";
        }
        return language;
    }

    // Property status can be null, manually define get method.
    public String getStatus() {
        if (status == null) {
            status = "Status Unknown";
        }
        return status;
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
