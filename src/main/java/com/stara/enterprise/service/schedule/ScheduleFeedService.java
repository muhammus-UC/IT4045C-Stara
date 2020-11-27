package com.stara.enterprise.service.schedule;

import com.stara.enterprise.dao.schedule.IScheduleFeedDAO;
import com.stara.enterprise.dao.show.IShowFeedDAO;
import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.show.ShowFeedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ScheduleFeedService implements IScheduleFeedService {
    @Autowired
    private IScheduleFeedDAO scheduleFeedDAO;

    /**
     * Fetch "ScheduleFeed" via DAO for country code given
     *
     * @param countryCode - Country code for country that user wants TV premiere schedule for (Ex: "US")
     * @return "ScheduleFeed" - List of ScheduleFeedItems detailing the episodes premiering today for countryCode
     * @throws IOException needs to be handled in case API call fails
     */
    @Override
    @Cacheable(value = "scheduleFeed")
    public List<ScheduleFeedItem> fetchScheduleFeed(String countryCode) throws IOException {
        return scheduleFeedDAO.fetchScheduleFeed(countryCode);
    }
}
