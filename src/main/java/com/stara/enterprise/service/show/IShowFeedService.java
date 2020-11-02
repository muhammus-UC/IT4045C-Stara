package com.stara.enterprise.service.show;

import com.stara.enterprise.dto.show.ShowFeedItem;

import java.io.IOException;
import java.util.List;

public interface IShowFeedService {
    /**
     * Fetch "ShowFeed" via DAO for show name given
     *
     * @param showName - Show name that user is looking for information on (Ex: "Community")
     * @return "ShowFeed" - List of ShowFeedItems with the name of or similar to showName
     * @throws IOException needs to be handled in case API call fails
     */
    List<ShowFeedItem> fetchShowFeed(String showName) throws IOException;
}
