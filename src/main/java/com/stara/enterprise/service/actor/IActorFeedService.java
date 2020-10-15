package com.stara.enterprise.service.actor;

import com.stara.enterprise.dto.actor.ActorFeed;

import java.io.IOException;
import java.util.List;

public interface IActorFeedService {
    List<ActorFeed> fetchActors(String actorName) throws IOException;
}
