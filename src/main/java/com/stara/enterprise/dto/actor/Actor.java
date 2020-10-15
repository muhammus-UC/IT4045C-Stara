package com.stara.enterprise.dto.actor;

import com.google.gson.annotations.SerializedName;
import com.stara.enterprise.dto.ImageURLs;
import lombok.Data;

public @Data
class Actor {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private ActorCountry country;
    @SerializedName("gender")
    private String gender;
    @SerializedName("image")
    private ImageURLs image;
}
