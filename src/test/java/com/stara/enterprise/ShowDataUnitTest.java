package com.stara.enterprise;

import com.stara.enterprise.dao.show.IShowFeedDAO;
import com.stara.enterprise.dto.ImageURL;
import com.stara.enterprise.dto.show.Show;
import com.stara.enterprise.dto.show.ShowFeedItem;
import com.stara.enterprise.service.show.IShowFeedService;
import com.stara.enterprise.service.show.ShowFeedService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShowDataUnitTest {
    private IShowFeedService showFeedService;
    private List<ShowFeedItem> showFeedItemListTested;

    @MockBean
    private IShowFeedDAO showFeedDAO;

    @Test
    void confirmNailedIt_outputsNailedIt() {
        Show show = new Show();
        show.setId(35093);
        show.setUrl("http://www.tvmaze.com/shows/35093/nailed-it");
        show.setName("Nailed It!");
        show.setLanguage("English");
        show.setStatus("To Be Determined");
        show.setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/256/642199.jpg"));

        assertEquals(35093, show.getId());
        assertEquals("http://www.tvmaze.com/shows/35093/nailed-it", show.getUrl());
        assertEquals("Nailed It!", show.getName());
        assertEquals("English", show.getLanguage());
        assertEquals("Status: To Be Determined", show.getStatus());
        assertEquals(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/256/642199.jpg"), show.getImage());

        String stringNailedIt = "Show Info - ID: 35093. URL: http://www.tvmaze.com/shows/35093/nailed-it. Name: Nailed It!. Language: English. Status: To Be Determined. Image URL: ImageURL(medium=http://static.tvmaze.com/uploads/images/medium_portrait/256/642199.jpg)";

        assertEquals(stringNailedIt, show.toString());

        ShowFeedItem showFeedItem = new ShowFeedItem();
        showFeedItem.setScore(26.3);
        showFeedItem.setShow(show);

        assertEquals("ShowFeedItem - Score: 26.3. " + stringNailedIt, showFeedItem.toString());
    }

    @Test
    void confirmGoodPlace_outputsGoodPlace() {
        Show show = new Show();
        show.setId(2790);
        show.setUrl("http://www.tvmaze.com/shows/2790/the-good-place");
        show.setName("The Good Place");
        show.setLanguage("English");
        show.setStatus("Ended");
        show.setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/207/519371.jpg"));

        assertEquals(2790, show.getId());
        assertEquals("http://www.tvmaze.com/shows/2790/the-good-place", show.getUrl());
        assertEquals("The Good Place", show.getName());
        assertEquals("English", show.getLanguage());
        assertEquals("Status: Ended", show.getStatus());
        assertEquals(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/207/519371.jpg"), show.getImage());

        String stringTheGoodPlace = "Show Info - ID: 2790. URL: http://www.tvmaze.com/shows/2790/the-good-place. Name: The Good Place. Language: English. Status: Ended. Image URL: ImageURL(medium=http://static.tvmaze.com/uploads/images/medium_portrait/207/519371.jpg)";

        assertEquals(stringTheGoodPlace, show.toString());

        ShowFeedItem showFeedItem = new ShowFeedItem();
        showFeedItem.setScore(30.6);
        showFeedItem.setShow(show);

        assertEquals("ShowFeedItem - Score: 30.6. " + stringTheGoodPlace, showFeedItem.toString());
    }

    // Confirms data which can be null from API is being handled properly.
    @Test
    void confirmNullShow_outputsNullShow() {
        Show show = new Show();

        assertEquals(0, show.getId());
        assertNull(show.getUrl());
        assertNull(show.getName());
        assertEquals("Language Unknown", show.getLanguage());
        assertEquals("Status: Unknown", show.getStatus());
        assertEquals(new ImageURL("images/null.svg"), show.getImage());

        assertEquals("Show Info - ID: 0. URL: null. Name: null. Language: Language Unknown. Status: Unknown. Image URL: ImageURL(medium=images/null.svg)", show.toString());
    }

    private void givenShowDataAreAvailable() throws IOException {
        Show show1 = new Show();
        show1.setId(318);
        show1.setUrl("http://www.tvmaze.com/shows/318/community");
        show1.setName("Community");
        show1.setLanguage("English");
        show1.setStatus("Ended");
        show1.setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/2/5134.jpg"));
        ShowFeedItem showFeedItem1 = new ShowFeedItem();
        showFeedItem1.setScore(50.0);
        showFeedItem1.setShow(show1);

        Show show2 = new Show();
        show2.setId(28145);
        show2.setUrl("http://www.tvmaze.com/shows/28145/community-life");
        show2.setName("Community Life");
        show2.setLanguage("English");
        show2.setStatus("To Be Determined");
        show2.setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/111/278554.jpg"));
        ShowFeedItem showFeedItem2 = new ShowFeedItem();
        showFeedItem2.setScore(25.0);
        showFeedItem2.setShow(show2);

        Show show3 = new Show();
        show3.setId(6191);
        show3.setUrl("http://www.tvmaze.com/shows/6191/diplomatic-immunity");
        show3.setName("Diplomatic Immunity");
        show3.setLanguage("English");
        show3.setStatus("Ended");
        show3.setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/23/59789.jpg"));
        ShowFeedItem showFeedItem3 = new ShowFeedItem();
        showFeedItem3.setScore(12.5);
        showFeedItem3.setShow(show3);

        List<ShowFeedItem> showFeedItemListInitData = new ArrayList<ShowFeedItem>();
        showFeedItemListInitData.add(showFeedItem1);
        showFeedItemListInitData.add(showFeedItem2);
        showFeedItemListInitData.add(showFeedItem3);

        Mockito.when(showFeedDAO.fetchShowFeed("Community")).thenReturn(showFeedItemListInitData);
        showFeedService = new ShowFeedService(showFeedDAO);

        // Reinitialize variable to ensure data across tests doesn't cause false positives/negatives
        showFeedItemListTested = new ArrayList<ShowFeedItem>();
    }

    @Test
    void searchForCommunity_returnsCommunity() throws IOException {
        givenShowDataAreAvailable();
        whenSearchForCommunity();
        thenResultContainsCommunity();
    }

    private void whenSearchForCommunity() throws IOException {
        showFeedItemListTested = showFeedService.fetchShowFeed("Community");
    }

    private void thenResultContainsCommunity() {
        boolean communityFound = false;

        for (ShowFeedItem showFeedItem : showFeedItemListTested) {
            if (
                    showFeedItem.getShow().getId() == 318 &&
                            showFeedItem.getShow().getUrl().equals("http://www.tvmaze.com/shows/318/community") &&
                            showFeedItem.getShow().getName().equals("Community") &&
                            showFeedItem.getShow().getLanguage().equals("English") &&
                            showFeedItem.getShow().getStatus().equals("Status: Ended") &&
                            showFeedItem.getShow().getImage().getMedium().equals("http://static.tvmaze.com/uploads/images/medium_portrait/2/5134.jpg") &&
                            showFeedItem.getScore() == 50.0
            ) {
                communityFound = true;
                break;
            }
        }

        assertTrue(communityFound);
    }

}
