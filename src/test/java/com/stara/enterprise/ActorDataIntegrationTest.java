package com.stara.enterprise;

import com.stara.enterprise.dto.actor.ActorFeedItem;
import com.stara.enterprise.service.actor.IActorFeedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActorDataIntegrationTest {

    @Autowired
    IActorFeedService actorFeedService;

    List<ActorFeedItem> actorFeed;

    @Test
    void actorDTO_isPopulated() {
        givenViewModelIsInitialized();
        whenActorFeedDataAreReadAndParsed();
        thenActorFeedShouldNotBeEmpty();
    }

    @Test
    void searchForTomHanks_returnsTomHanks() {
        givenViewModelIsInitialized();
        whenSearchForTomHanks();
        andWhenSleep();
        thenActorFeedShouldContainTomHanks();
    }

    @Test
    void searchForGarbage_returnsNothing() {
        givenViewModelIsInitialized();
        whenSearchForGarbage();
        andWhenSleep();
        thenActorFeedShouldBeEmpty();
    }

    private void andWhenSleep() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void givenViewModelIsInitialized() {
        assertNotNull(actorFeedService);
        actorFeed = null;
    }

    private void whenActorFeedDataAreReadAndParsed() {
        try {
            actorFeed = actorFeedService.fetchActorFeed("Joel McHale");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void thenActorFeedShouldNotBeEmpty() {
        assertTrue(actorFeed.size() > 0);
    }

    private void whenSearchForTomHanks() {
        try {
            actorFeed = actorFeedService.fetchActorFeed("Tom Hanks");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void thenActorFeedShouldContainTomHanks() {
        boolean tomHanksFound = false;

        for (ActorFeedItem actorFeedItem : actorFeed) {
            if (
                actorFeedItem.getActor().getId() == 46432 &&
                actorFeedItem.getActor().getUrl().equals("http://www.tvmaze.com/people/46432/tom-hanks") &&
                actorFeedItem.getActor().getName().equals("Tom Hanks") &&
                actorFeedItem.getActor().getCountry().getName().equals("United States") &&
                actorFeedItem.getActor().getGender().equals("Male") &&
                actorFeedItem.getActor().getImage().getMedium().equals("http://static.tvmaze.com/uploads/images/medium_portrait/28/72307.jpg") &&
                !actorFeedItem.getScore().isNaN()
            ) {
                tomHanksFound = true;
                break;
            }
        }

        assertTrue(tomHanksFound);
    }

    private void whenSearchForGarbage() {
        try {
            actorFeed = actorFeedService.fetchActorFeed("sklujapouetllkjsdau");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void thenActorFeedShouldBeEmpty() {
        assertEquals(0, actorFeed.size());
    }
}
