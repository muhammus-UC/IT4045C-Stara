package com.stara.enterprise.service.actor;

import com.stara.enterprise.dto.actor.ActorFeedItem;

import java.io.IOException;
import java.util.List;

public interface IActorFeedService {
    List<ActorFeedItem> fetchActorFeed(String actorName) throws IOException;
}
