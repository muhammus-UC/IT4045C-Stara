package com.stara.enterprise.dao.show;

import com.stara.enterprise.dto.show.ShowFeedItem;

import java.io.IOException;
import java.util.List;

public interface IShowFeedDAO {
    List<ShowFeedItem> fetchShowFeed(String showName) throws IOException;
}
