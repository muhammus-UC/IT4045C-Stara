package com.stara.enterprise.service.schedule;

import com.stara.enterprise.dao.schedule.IScheduleFeedDAO;
import com.stara.enterprise.dao.show.IShowFeedDAO;
import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.show.ShowFeedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ScheduleFeedService implements IScheduleFeedService {
    @Autowired
    private IScheduleFeedDAO scheduleFeedDAO;

    @Override
    public List<ScheduleFeedItem> fetchScheduleFeed(String countryCode) throws IOException {
        return scheduleFeedDAO.fetchScheduleFeed(countryCode);
    }
}
