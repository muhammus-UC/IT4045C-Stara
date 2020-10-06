package com.stara.enterprise.service;

import com.stara.enterprise.dto.ShowFeed;

import java.io.IOException;
import java.util.List;

public interface IShowFeedService {
    List<ShowFeed> fetchShows(String showName) throws IOException;
}
