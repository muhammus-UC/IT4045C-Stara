package com.stara.enterprise.dao.show;

import com.stara.enterprise.dto.show.ShowFeedItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IShowFeedRetrofitDAO {
    /**
     * Uses Retrofit to communicate with TVMaze API to get list of ShowFeedItems
     * TVMaze API Reference: https://www.tvmaze.com/api
     * GET annotation base domain is defined in dao/RetrofitClientInstance
     *
     * @param showName - Show name that user is looking for information on (Ex: "Community")
     * @return "ShowFeed" - List of ShowFeedItems with the name of or similar to showName
     */
    @GET("/search/shows")
    Call<List<ShowFeedItem>> getShowFeed(@Query("q") String showName);
}
