package com.stara.enterprise.dao.actor;

import com.stara.enterprise.dto.actor.ActorFeed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IActorFeedRetrofitDAO {
    @GET("/search/people")
    Call<List<ActorFeed>> getActors(@Query("q") String actorName);
}
