package com.stara.enterprise.dao.schedule;

import com.stara.enterprise.dao.RetrofitClientInstance;
import com.stara.enterprise.dao.show.IShowFeedRetrofitDAO;
import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.show.ShowFeedItem;
import org.springframework.stereotype.Repository;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

@Repository
public class ScheduleFeedDAO implements IScheduleFeedDAO {
    /**
     * Fetch "ScheduleFeed" from the API for country code given
     *
     * @param countryCode - Country code for country that user wants TV premiere schedule for (Ex: "US")
     * @return "ScheduleFeed" - List of ScheduleFeedItems detailing the episodes premiering today for countryCode given
     * @throws IOException needs to be handled in case API call fails
     */
    @Override
    public List<ScheduleFeedItem> fetchScheduleFeed(String countryCode) throws IOException {
        Retrofit retrofitInstance = RetrofitClientInstance.getRetrofitInstance();
        IScheduleFeedRetrofitDAO scheduleFeedRetrofitDAO = retrofitInstance.create(IScheduleFeedRetrofitDAO.class);
        Call<List<ScheduleFeedItem>> scheduleFeedCall = scheduleFeedRetrofitDAO.getScheduleFeed(countryCode);
        Response<List<ScheduleFeedItem>> execute = scheduleFeedCall.execute();
        List<ScheduleFeedItem> scheduleFeed = execute.body();
        return scheduleFeed;
    }
}
