package com.stara.enterprise.dto.show;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

// ShowFeedItem is needed to access nested JSON with Show information we actually care about.
public @Data
class ShowFeedItem {
    // Double used to indicate which result is closest to search term
    @SerializedName("score")
    private Double score;
    // Show object used to parse nested JSON
    @SerializedName("show")
    private Show show;

    public String toString() {
        return "ShowFeedItem - " +
                "Score: " + score +
                ". " + show.toString();
    }
}
