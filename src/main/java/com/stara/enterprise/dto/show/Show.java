package com.stara.enterprise.dto.show;

import com.google.gson.annotations.SerializedName;
import com.stara.enterprise.dto.ImageURLs;
import lombok.Data;

public @Data
class Show {
    // Int used to uniquely identify Show from other Shows on TVMaze Database. Note: This doesn't uniquely identify Show from Actors.
    @SerializedName("id")
    private int id;
    // String of URL to page for Show on TVMaze.
    @SerializedName("url")
    private String url;
    // String of name of Show.
    @SerializedName("name")
    private String name;
    // String of language Show was originally aired in.
    @SerializedName("language")
    private String language;
    // String indicating Show's current status, whether it's running etc.
    @SerializedName("status")
    private String status;
    // ImageURLs object used to parse nested JSON containing URLs to images of Show.
    @SerializedName("image")
    private ImageURLs image;

    public String toString() {
        return "Show Info - ID: " + id +
                ". URL: " + url +
                ". Name: " + name +
                ". Language: " + language +
                ". " + status +
                ". Image URL: " + image;
    }

    /**
     * Property language may be null when retrieved from TVMaze API, manually defining get method
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
     * Property status may be null when retrieved from TVMaze API, manually defining get method
     *
     * @return status if not null, otherwise "Status Unknown"
     */
    public String getStatus() {
        if (status == null) {
            status = "Status: Unknown";
        } else if (!status.contains("Status: ")) {
            // Inefficient way avoid printing Status multiple times. This is being done because Android app adds "Status:" when saving a favorite.
            // Should be able to fix this on Android app to not have to do this.
            status = "Status: " + status;
        }
        return status;
    }

    /**
     * Property image may be null when retrieved from TVMaze API, manually define get method
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
