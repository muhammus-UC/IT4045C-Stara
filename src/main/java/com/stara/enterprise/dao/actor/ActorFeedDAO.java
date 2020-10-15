package com.stara.enterprise.dao.actor;

import com.stara.enterprise.dao.RetrofitClientInstance;
import com.stara.enterprise.dto.actor.ActorFeed;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Repository
public class ActorFeedDAO implements IActorFeedDAO {
    @Override
    public List<ActorFeed> fetchActors(String actorName) throws IOException {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        IActorFeedRetrofitDAO actorFeedRetrofitDAO = retrofitInstance.create(IActorFeedRetrofitDAO.class);
        Call<List<ActorFeed>> searchResultsActorFeed = actorFeedRetrofitDAO.getActors(actorName);
        Response<List<ActorFeed>> execute = searchResultsActorFeed.execute();
        List<ActorFeed> actors = execute.body();
        return actors;
    }
}
