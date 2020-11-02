package com.stara.enterprise.dto.actor;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class ActorFeedItem {
    @SerializedName("score")
    private Double score;
    @SerializedName("person")
    private Actor actor;

    public String toString() {
        return "ActorFeedItem - " +
                "Score: " + score +
                ". " + actor.toString();
    }
}
