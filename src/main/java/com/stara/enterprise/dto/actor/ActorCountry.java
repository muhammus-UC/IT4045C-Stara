package com.stara.enterprise.dto.actor;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class ActorCountry {
    @SerializedName("name")
    private String name;

    public String toString() {
        return name;
    }

    public ActorCountry() {}

    public ActorCountry(String country) {
        this.name = country;
    }
}
