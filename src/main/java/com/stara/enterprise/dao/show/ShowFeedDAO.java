package com.stara.enterprise.dao.show;

import com.stara.enterprise.dao.RetrofitClientInstance;
import com.stara.enterprise.dto.show.ShowFeed;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Repository
public class ShowFeedDAO implements IShowFeedDAO {
    @Override
    public List<ShowFeed> fetchShows(String showName) throws IOException {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        IShowFeedRetrofitDAO showFeedRetrofitDAO = retrofitInstance.create(IShowFeedRetrofitDAO.class);
        Call<List<ShowFeed>> searchResultsShowFeed = showFeedRetrofitDAO.getShows(showName);
        Response<List<ShowFeed>> execute = searchResultsShowFeed.execute();
        List<ShowFeed> shows = execute.body();
        return shows;
    }
}
