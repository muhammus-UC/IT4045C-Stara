package com.stara.enterprise.dao.schedule;

import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.show.ShowFeedItem;

import java.io.IOException;
import java.util.List;

public interface IScheduleFeedDAO {
    List<ScheduleFeedItem> fetchScheduleFeed(String countryCode) throws IOException;
}
