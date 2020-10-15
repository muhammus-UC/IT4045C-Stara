package com.stara.enterprise;

import com.stara.enterprise.dto.ShowFeed;
import com.stara.enterprise.service.IShowFeedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShowDataIntegrationTest {

    @Autowired
    IShowFeedService showFeedService;

    List<ShowFeed> showFeed;

    @Test
    void actorDTO_isPopulated() {
        givenViewModelIsInitialized();
        whenShowFeedDataAreReadAndParsed();
        thenShowFeedShouldNotBeEmpty();
    }

    @Test
    void searchForBlackBooks_returnsBlackBooks() {
        givenViewModelIsInitialized();
        whenSearchForBlackBooks();
        andWhenSleep();
        thenShowFeedShouldContainBlackBooks();
    }

    @Test
    void searchForGarbage_returnsNothing() {
        givenViewModelIsInitialized();
        whenSearchForGarbage();
        andWhenSleep();
        thenShowFeedShouldBeEmpty();
    }

    private void andWhenSleep() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void givenViewModelIsInitialized() {
        assertNotNull(showFeedService);
        showFeed = null;
    }

    private void whenShowFeedDataAreReadAndParsed() {
        try {
            showFeed = showFeedService.fetchShows("Community");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void thenShowFeedShouldNotBeEmpty() {
        assertTrue(showFeed.size() > 0);
    }

    private void whenSearchForBlackBooks() {
        try {
            showFeed = showFeedService.fetchShows("Black Books");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void thenShowFeedShouldContainBlackBooks() {
        boolean blackBooksFound = false;

        for (ShowFeed showFeedItem : showFeed) {
            if (
                showFeedItem.getShow().getId() == 1641 &&
                showFeedItem.getShow().getName().equals("Black Books") &&
                showFeedItem.getShow().getLanguage().equals("English") &&
                showFeedItem.getShow().getStatus().equals("Ended") &&
                showFeedItem.getShow().getImage().getMedium().equals("http://static.tvmaze.com/uploads/images/medium_portrait/81/204617.jpg")
            ) {
                blackBooksFound = true;
                break;
            }
        }

        assertTrue(blackBooksFound);
    }

    private void whenSearchForGarbage() {
        try {
            showFeed = showFeedService.fetchShows("sklujapouetllkjsdau");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void thenShowFeedShouldBeEmpty() {
        assertEquals(0, showFeed.size());
    }
}
