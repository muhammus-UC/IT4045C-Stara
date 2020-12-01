package com.stara.enterprise.dto.actor;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class ActorCountry {
    // String of name of country Actor was born in.
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
