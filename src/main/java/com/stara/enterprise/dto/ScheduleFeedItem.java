package com.stara.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import com.stara.enterprise.dto.show.Show;
import lombok.Data;

public @Data
class ScheduleFeedItem {
    @SerializedName("name")
    private String episodeName;
    @SerializedName("url")
    private String url;
    @SerializedName("airtime")
    private String airtime;
    // Show object used to parse nested JSON
    @SerializedName("show")
    private Show show;

    public String toString() {
        return "ScheduleFeedItem - Episode: " + episodeName +
                ". Airtime: " + airtime +
                ". URL: " + url +
                ". Show: " + show;
    }
}
