package com.stara.enterprise.dao.show;

import com.stara.enterprise.dto.show.ShowFeed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface IShowFeedRetrofitDAO {
    @GET("/search/shows")
    Call<List<ShowFeed>> getShows(@Query("q") String showName);
}
