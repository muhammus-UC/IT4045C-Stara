package com.stara.enterprise.dao.show;

import com.stara.enterprise.dto.ShowFeed;

import java.io.IOException;
import java.util.List;

public interface IShowFeedDAO {
    List<ShowFeed> fetchShows(String showName) throws IOException;
}
