package com.stara.enterprise.dao.actor;

import com.stara.enterprise.dto.actor.ActorFeed;

import java.io.IOException;
import java.util.List;

public interface IActorFeedDAO {
    List<ActorFeed> fetchActors(String actorName) throws IOException;
}
