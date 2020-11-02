package com.stara.enterprise.service.actor;

import com.stara.enterprise.dto.actor.ActorFeedItem;

import java.io.IOException;
import java.util.List;

public interface IActorFeedService {
    /**
     * Fetch "ActorFeed" via DAO for actor name given
     *
     * @param actorName - Actor name that user is looking for information on (Ex: "Bradd Pitt")
     * @return "ActorFeed" - List of ActorFeedItems with the name of or similar to actorName
     * @throws IOException needs to be handled in case API call fails
     */
    List<ActorFeedItem> fetchActorFeed(String actorName) throws IOException;
}
