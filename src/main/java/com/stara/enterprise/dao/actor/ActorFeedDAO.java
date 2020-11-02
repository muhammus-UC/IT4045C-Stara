package com.stara.enterprise.dao.actor;

import com.stara.enterprise.dao.RetrofitClientInstance;
import com.stara.enterprise.dto.actor.ActorFeedItem;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Repository
public class ActorFeedDAO implements IActorFeedDAO {
    @Override
    public List<ActorFeedItem> fetchActorFeed(String actorName) throws IOException {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        IActorFeedRetrofitDAO actorFeedRetrofitDAO = retrofitInstance.create(IActorFeedRetrofitDAO.class);
        Call<List<ActorFeedItem>> actorFeedCall = actorFeedRetrofitDAO.getActorFeed(actorName);
        Response<List<ActorFeedItem>> execute = actorFeedCall.execute();
        List<ActorFeedItem> actorFeed = execute.body();
        return actorFeed;
    }
}
