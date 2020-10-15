package com.stara.enterprise.service.show;

import com.stara.enterprise.dto.show.ShowFeed;

import java.io.IOException;
import java.util.List;

public interface IShowFeedService {
    List<ShowFeed> fetchShows(String showName) throws IOException;
}
