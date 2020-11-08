package com.stara.enterprise.service.actor;

import com.stara.enterprise.dao.actor.IActorFeedDAO;
import com.stara.enterprise.dto.actor.ActorFeedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ActorFeedService implements IActorFeedService {
    @Autowired
    private IActorFeedDAO actorFeedDAO;

    /**
     * Fetch "ActorFeed" via DAO for actor name given
     *
     * @param actorName - Actor name that user is looking for information on (Ex: "Bradd Pitt")
     * @return "ActorFeed" - List of ActorFeedItems with the name of or similar to actorName
     * @throws IOException needs to be handled in case API call fails
     */
    @Override
    public List<ActorFeedItem> fetchActorFeed(String actorName) throws IOException {
        return actorFeedDAO.fetchActorFeed(actorName);
    }
}
