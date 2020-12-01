package com.stara.enterprise.dto;

import com.google.gson.annotations.SerializedName;
import com.stara.enterprise.dto.show.Show;
import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public @Data
class ScheduleFeedItem {
    // String of name of episode scheduled.
    @SerializedName("name")
    private String episodeName;
    // String of URL to TVMaze page for scheduled episode.
    @SerializedName("url")
    private String url;
    // String of airtime for scheduled episode.
    @SerializedName("airtime")
    private String airtime;
    // Show object used to parse nested JSON corresponding scheduled episode's Show.
    @SerializedName("show")
    private Show show;

    public String toString() {
        return "ScheduleFeedItem - Episode: " + episodeName +
                ". Airtime: " + airtime +
                ". URL: " + url +
                ". Show: " + show;
    }

    /**
     * Convert property airtime from 24-hour time to 12-hour time when being accessed
     * Reference: https://stackoverflow.com/a/49326758
     *
     * @return airtime in 12 hour-time format.
     */
    public String getAirtime() {
        return LocalTime.parse(airtime).format(DateTimeFormatter.ofPattern("hh:mm a"));
    }
}
