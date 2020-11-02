package com.stara.enterprise.dao.schedule;

import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.show.ShowFeedItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IScheduleFeedRetrofitDAO {
    /**
     * Uses Retrofit to communicate with TVMaze API to get list of ScheduleFeedItems
     * TVMaze API Reference: https://www.tvmaze.com/api
     * @GET based domain is defined in dao/RetrofitClientInstance
     *
     * @param countryCode - Country code for country that user wants TV premiere schedule for (Ex: "US")
     * @return "ScheduleFeed" - List of ScheduleFeedItems detailing the episodes premiering today for countryCode given
     */
    @GET("/schedule")
    Call<List<ScheduleFeedItem>> getScheduleFeed(@Query("country") String countryCode);
}
