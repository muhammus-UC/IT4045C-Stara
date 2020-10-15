package com.stara.enterprise.dto.actor;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class ActorFeed {
    @SerializedName("score")
    private Double score;
    @SerializedName("person")
    private Actor actor;
}
