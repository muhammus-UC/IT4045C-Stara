package com.stara.enterprise.service.actor;

import com.stara.enterprise.dao.actor.IActorFeedDAO;
import com.stara.enterprise.dto.actor.ActorFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ActorFeedService implements IActorFeedService {
    @Autowired
    private IActorFeedDAO actorFeedDAO;

    @Override
    public List<ActorFeed> fetchActors(String actorName) throws IOException {
        return actorFeedDAO.fetchActors(actorName);
    }
}
