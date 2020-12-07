package com.stara.enterprise;

import com.stara.enterprise.dao.schedule.IScheduleFeedDAO;
import com.stara.enterprise.dto.ImageURL;
import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.show.Show;
import com.stara.enterprise.service.schedule.IScheduleFeedService;
import com.stara.enterprise.service.schedule.ScheduleFeedService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ScheduleDataUnitTest {
    private IScheduleFeedService scheduleFeedService;
    private List<ScheduleFeedItem> scheduleFeedItemListTested;

    @MockBean
    private IScheduleFeedDAO scheduleFeedDAO;

    @Test
    void confirmScheduledCBSEpisode_outputsScheduledCBSEpisode() {
        ScheduleFeedItem scheduleFeedItem = new ScheduleFeedItem();
        scheduleFeedItem.setEpisodeName("Episode 44");
        scheduleFeedItem.setUrl("http://www.tvmaze.com/episodes/1954168/cbs-news-sunday-morning-2020-11-01-episode-44");
        scheduleFeedItem.setAirtime("09:00");
        scheduleFeedItem.setShow(new Show());
        scheduleFeedItem.getShow().setId(15779);
        scheduleFeedItem.getShow().setUrl("http://www.tvmaze.com/shows/15779/cbs-news-sunday-morning");
        scheduleFeedItem.getShow().setName("CBS News Sunday Morning");
        scheduleFeedItem.getShow().setLanguage("English");
        scheduleFeedItem.getShow().setStatus("Running");
        scheduleFeedItem.getShow().setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/237/592585.jpg"));

        assertEquals("Episode 44", scheduleFeedItem.getEpisodeName());
        assertEquals("http://www.tvmaze.com/episodes/1954168/cbs-news-sunday-morning-2020-11-01-episode-44", scheduleFeedItem.getUrl());
        assertEquals("09:00 AM", scheduleFeedItem.getAirtime());
        assertEquals(15779, scheduleFeedItem.getShow().getId());
        assertEquals("http://www.tvmaze.com/shows/15779/cbs-news-sunday-morning", scheduleFeedItem.getShow().getUrl());
        assertEquals("CBS News Sunday Morning", scheduleFeedItem.getShow().getName());
        assertEquals("English", scheduleFeedItem.getShow().getLanguage());
        assertEquals("Status: Running", scheduleFeedItem.getShow().getStatus());
        assertEquals(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/237/592585.jpg"), scheduleFeedItem.getShow().getImage());

        String stringEpisode44 = "ScheduleFeedItem - Episode: Episode 44. Airtime: 09:00. URL: http://www.tvmaze.com/episodes/1954168/cbs-news-sunday-morning-2020-11-01-episode-44. " +
                "Show: Show Info - ID: 15779. URL: http://www.tvmaze.com/shows/15779/cbs-news-sunday-morning. Name: CBS News Sunday Morning. Language: English. Status: Running. Image URL: ImageURL(medium=http://static.tvmaze.com/uploads/images/medium_portrait/237/592585.jpg)";

        assertEquals(stringEpisode44, scheduleFeedItem.toString());
    }

    @Test
    void confirmScheduledGirlMeetsFarmEpisode_outputsScheduledGirlMeetsFarmEpisode() {
        ScheduleFeedItem scheduleFeedItem = new ScheduleFeedItem();
        scheduleFeedItem.setEpisodeName("Dinner Staycation");
        scheduleFeedItem.setUrl("http://www.tvmaze.com/episodes/1953551/girl-meets-farm-6x10-dinner-staycation");
        scheduleFeedItem.setAirtime("11:00");
        scheduleFeedItem.setShow(new Show());
        scheduleFeedItem.getShow().setId(36765);
        scheduleFeedItem.getShow().setUrl("http://www.tvmaze.com/shows/36765/girl-meets-farm");
        scheduleFeedItem.getShow().setName("Girl Meets Farm");
        scheduleFeedItem.getShow().setLanguage("English");
        scheduleFeedItem.getShow().setStatus("Running");
        scheduleFeedItem.getShow().setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/158/396187.jpg"));

        assertEquals("Dinner Staycation", scheduleFeedItem.getEpisodeName());
        assertEquals("http://www.tvmaze.com/episodes/1953551/girl-meets-farm-6x10-dinner-staycation", scheduleFeedItem.getUrl());
        assertEquals("11:00 AM", scheduleFeedItem.getAirtime());
        assertEquals(36765, scheduleFeedItem.getShow().getId());
        assertEquals("http://www.tvmaze.com/shows/36765/girl-meets-farm", scheduleFeedItem.getShow().getUrl());
        assertEquals("Girl Meets Farm", scheduleFeedItem.getShow().getName());
        assertEquals("English", scheduleFeedItem.getShow().getLanguage());
        assertEquals("Status: Running", scheduleFeedItem.getShow().getStatus());
        assertEquals(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/158/396187.jpg"), scheduleFeedItem.getShow().getImage());

        String stringEpisodeDinner = "ScheduleFeedItem - Episode: Dinner Staycation. Airtime: 11:00. URL: http://www.tvmaze.com/episodes/1953551/girl-meets-farm-6x10-dinner-staycation. " +
                "Show: Show Info - ID: 36765. URL: http://www.tvmaze.com/shows/36765/girl-meets-farm. Name: Girl Meets Farm. Language: English. Status: Running. Image URL: ImageURL(medium=http://static.tvmaze.com/uploads/images/medium_portrait/158/396187.jpg)";

        assertEquals(stringEpisodeDinner, scheduleFeedItem.toString());
    }

    private void givenScheduleDataAreAvailable() throws IOException {
        ScheduleFeedItem scheduleFeedItem1 = new ScheduleFeedItem();
        scheduleFeedItem1.setEpisodeName("Episode 44");
        scheduleFeedItem1.setUrl("http://www.tvmaze.com/episodes/1954168/cbs-news-sunday-morning-2020-11-01-episode-44");
        scheduleFeedItem1.setAirtime("09:00");
        scheduleFeedItem1.setShow(new Show());
        scheduleFeedItem1.getShow().setId(15779);
        scheduleFeedItem1.getShow().setUrl("http://www.tvmaze.com/shows/15779/cbs-news-sunday-morning");
        scheduleFeedItem1.getShow().setName("CBS News Sunday Morning");
        scheduleFeedItem1.getShow().setLanguage("English");
        scheduleFeedItem1.getShow().setStatus("Running");
        scheduleFeedItem1.getShow().setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/237/592585.jpg"));

        ScheduleFeedItem scheduleFeedItem2 = new ScheduleFeedItem();
        scheduleFeedItem2.setEpisodeName("Dinner Staycation");
        scheduleFeedItem2.setUrl("http://www.tvmaze.com/episodes/1953551/girl-meets-farm-6x10-dinner-staycation");
        scheduleFeedItem2.setAirtime("11:00");
        scheduleFeedItem2.setShow(new Show());
        scheduleFeedItem2.getShow().setId(36765);
        scheduleFeedItem2.getShow().setUrl("http://www.tvmaze.com/shows/36765/girl-meets-farm");
        scheduleFeedItem2.getShow().setName("Girl Meets Farm");
        scheduleFeedItem2.getShow().setLanguage("English");
        scheduleFeedItem2.getShow().setStatus("Running");
        scheduleFeedItem2.getShow().setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/158/396187.jpg"));

        List<ScheduleFeedItem> scheduleFeedItemListInitData = new ArrayList<ScheduleFeedItem>();
        scheduleFeedItemListInitData.add(scheduleFeedItem1);
        scheduleFeedItemListInitData.add(scheduleFeedItem2);

        Mockito.when(scheduleFeedDAO.fetchScheduleFeed("US")).thenReturn(scheduleFeedItemListInitData);
        scheduleFeedService = new ScheduleFeedService(scheduleFeedDAO);

        // Reinitialize variable to ensure data across tests doesn't cause false positives/negatives
        scheduleFeedItemListTested = new ArrayList<ScheduleFeedItem>();
    }

    @Test
    void searchForUSSchedule_returnsUSSchedule() throws IOException {
        givenScheduleDataAreAvailable();
        whenSearchForUSSchedule();
        thenResultContainsCBSNews();
    }

    private void whenSearchForUSSchedule() throws IOException {
        scheduleFeedItemListTested = scheduleFeedService.fetchScheduleFeed("US");
    }

    private void thenResultContainsCBSNews() {
        boolean cbsNewsFound = false;

        for (ScheduleFeedItem scheduleFeedItem : scheduleFeedItemListTested) {
            if (
                    scheduleFeedItem.getEpisodeName().equals("Episode 44") &&
                            scheduleFeedItem.getUrl().equals("http://www.tvmaze.com/episodes/1954168/cbs-news-sunday-morning-2020-11-01-episode-44") &&
                            scheduleFeedItem.getAirtime().equals("09:00 AM") &&
                            scheduleFeedItem.getShow().getId() == 15779 &&
                            scheduleFeedItem.getShow().getUrl().equals("http://www.tvmaze.com/shows/15779/cbs-news-sunday-morning") &&
                            scheduleFeedItem.getShow().getName().equals("CBS News Sunday Morning") &&
                            scheduleFeedItem.getShow().getLanguage().equals("English") &&
                            scheduleFeedItem.getShow().getStatus().equals("Status: Running") &&
                            scheduleFeedItem.getShow().getImage().getMedium().equals("http://static.tvmaze.com/uploads/images/medium_portrait/237/592585.jpg")
            ) {
                cbsNewsFound = true;
                break;
            }
        }

        assertTrue(cbsNewsFound);
    }
}
