package com.stara.enterprise.service.show;

import com.stara.enterprise.dao.show.IShowFeedDAO;
import com.stara.enterprise.dto.show.ShowFeedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ShowFeedService implements IShowFeedService {
    @Autowired
    private IShowFeedDAO showFeedDAO;

    @Override
    public List<ShowFeedItem> fetchShowFeed(String showName) throws IOException {
        return showFeedDAO.fetchShowFeed(showName);
    }
}
