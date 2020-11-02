package com.stara.enterprise.dto.show;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class ShowFeedItem {
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
