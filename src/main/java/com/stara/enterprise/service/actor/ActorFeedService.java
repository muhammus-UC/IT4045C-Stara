package com.stara.enterprise.service.actor;

import com.stara.enterprise.dao.actor.IActorFeedDAO;
import com.stara.enterprise.dto.actor.ActorFeedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ActorFeedService implements IActorFeedService {
    @Autowired
    private IActorFeedDAO actorFeedDAO;

    @Override
    public List<ActorFeedItem> fetchActorFeed(String actorName) throws IOException {
        return actorFeedDAO.fetchActorFeed(actorName);
    }
}
