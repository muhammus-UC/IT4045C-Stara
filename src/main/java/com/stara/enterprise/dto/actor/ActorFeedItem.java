package com.stara.enterprise.dto.actor;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

// ActorFeedItem is needed to access nested JSON with Actor information we actually care about.
public @Data
class ActorFeedItem {
    // Double used to indicate which result is closest to search term
    @SerializedName("score")
    private Double score;
    // Actor object used to parse nested JSON
    @SerializedName("person")
    private Actor actor;

    public String toString() {
        return "ActorFeedItem - " +
                "Score: " + score +
                ". " + actor.toString();
    }
}
