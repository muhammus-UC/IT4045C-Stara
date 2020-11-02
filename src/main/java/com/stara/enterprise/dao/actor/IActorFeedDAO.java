package com.stara.enterprise.dao.actor;

import com.stara.enterprise.dto.actor.ActorFeedItem;

import java.io.IOException;
import java.util.List;

public interface IActorFeedDAO {
    List<ActorFeedItem> fetchActorFeed(String actorName) throws IOException;
}
