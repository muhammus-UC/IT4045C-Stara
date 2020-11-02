package com.stara.enterprise.service.schedule;

import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.show.ShowFeedItem;

import java.io.IOException;
import java.util.List;

public interface IScheduleFeedService {
    /**
     * Fetch "ScheduleFeed" via DAO for country code given
     *
     * @param countryCode - Country code for country that user wants TV premiere schedule for (Ex: "US")
     * @return "ScheduleFeed" - List of ScheduleFeedItems detailing the episodes premiering today for countryCode
     * @throws IOException needs to be handled in case API call fails
     */
    List<ScheduleFeedItem> fetchScheduleFeed(String countryCode) throws IOException;
}
