package com.stara.enterprise.service.show;

import com.stara.enterprise.dto.show.ShowFeedItem;

import java.io.IOException;
import java.util.List;

public interface IShowFeedService {
    List<ShowFeedItem> fetchShowFeed(String showName) throws IOException;
}
