package com.stara.enterprise.dto.show;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

public @Data
class ShowFeed {
    @SerializedName("score")
    private Double score;
    @SerializedName("show")
    private Show show;
}
