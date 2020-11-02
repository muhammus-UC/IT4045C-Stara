package com.stara.enterprise.dto.actor;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class ActorFeedItem {
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
