package com.stara.enterprise.service.show;

import com.stara.enterprise.dao.show.IShowFeedDAO;
import com.stara.enterprise.dto.show.ShowFeedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ShowFeedService implements IShowFeedService {
    @Autowired
    private IShowFeedDAO showFeedDAO;

    /**
     * Fetch "ShowFeed" via DAO for show name given
     *
     * @param showName - Show name that user is looking for information on (Ex: "Community")
     * @return "ShowFeed" - List of ShowFeedItems with the name of or similar to showName
     * @throws IOException needs to be handled in case API call fails
     */
    @Override
    @Cacheable(value="showFeed")
    public List<ShowFeedItem> fetchShowFeed(String showName) throws IOException {
        return showFeedDAO.fetchShowFeed(showName);
    }
}
