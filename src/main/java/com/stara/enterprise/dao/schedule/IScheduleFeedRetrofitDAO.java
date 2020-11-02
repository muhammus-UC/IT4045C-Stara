package com.stara.enterprise.dao.schedule;

import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.show.ShowFeedItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IScheduleFeedRetrofitDAO {
    @GET("/schedule")
    Call<List<ScheduleFeedItem>> getScheduleFeed(@Query("country") String countryCode);
}
