package com.stara.enterprise.dao.actor;

import com.stara.enterprise.dto.actor.ActorFeedItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IActorFeedRetrofitDAO {
    /**
     * Uses Retrofit to communicate with TVMaze API to get list of ActorFeedItems
     * TVMaze API Reference: https://www.tvmaze.com/api
     * GET annotation base domain is defined in dao/RetrofitClientInstance
     *
     * @param actorName - Actor name that user is looking for information on (Ex: "Brad Pitt")
     * @return "ActorFeed" - List of ActorFeedItems with the name of or similar to actorName
     */
    @GET("/search/people")
    Call<List<ActorFeedItem>> getActorFeed(@Query("q") String actorName);
}
