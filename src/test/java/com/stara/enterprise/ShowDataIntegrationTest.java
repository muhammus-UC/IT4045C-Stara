package com.stara.enterprise;

import com.stara.enterprise.dto.show.ShowFeedItem;
import com.stara.enterprise.service.show.IShowFeedService;
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

    List<ShowFeedItem> showFeed;

    @Test
    void showDTO_isPopulated() {
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
            showFeed = showFeedService.fetchShowFeed("Community");
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
            showFeed = showFeedService.fetchShowFeed("Black Books");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void thenShowFeedShouldContainBlackBooks() {
        boolean blackBooksFound = false;

        for (ShowFeedItem showFeedItem : showFeed) {
            if (
                showFeedItem.getShow().getId() == 1641 &&
                showFeedItem.getShow().getUrl().equals("http://www.tvmaze.com/shows/1641/black-books") &&
                showFeedItem.getShow().getName().equals("Black Books") &&
                showFeedItem.getShow().getLanguage().equals("English") &&
                showFeedItem.getShow().getStatus().equals("Ended") &&
                showFeedItem.getShow().getImage().getImageUrl().equals("http://static.tvmaze.com/uploads/images/medium_portrait/81/204617.jpg")
            ) {
                blackBooksFound = true;
                break;
            }
        }

        assertTrue(blackBooksFound);
    }

    private void whenSearchForGarbage() {
        try {
            showFeed = showFeedService.fetchShowFeed("sklujapouetllkjsdau");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void thenShowFeedShouldBeEmpty() {
        assertEquals(0, showFeed.size());
    }
}
