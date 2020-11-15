package com.stara.enterprise;

import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.show.Show;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ScheduleDataUnitTest {

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
        assertEquals("Episode 44", scheduleFeedItem.getEpisodeName());
        assertEquals("http://www.tvmaze.com/episodes/1954168/cbs-news-sunday-morning-2020-11-01-episode-44", scheduleFeedItem.getUrl());
        assertEquals("09:00", scheduleFeedItem.getAirtime());
        assertEquals(15779, scheduleFeedItem.getShow().getId());
        assertEquals("http://www.tvmaze.com/shows/15779/cbs-news-sunday-morning", scheduleFeedItem.getShow().getUrl());
        assertEquals("CBS News Sunday Morning", scheduleFeedItem.getShow().getName());
        assertEquals("English", scheduleFeedItem.getShow().getLanguage());
        assertEquals("Status: Running", scheduleFeedItem.getShow().getStatus());
    }

    @Test
    void confirmScheduledGirlMeetsFarmEpisode_outputsScheduledGirlMeetsFarmEpisode() {
        ScheduleFeedItem scheduleFeedItem = new ScheduleFeedItem();
        scheduleFeedItem.setEpisodeName("Dinner Staycation");
        scheduleFeedItem.setUrl("http://www.tvmaze.com/episodes/1953551/girl-meets-farm-6x10-dinner-staycation");
        scheduleFeedItem.setAirtime("11:00");
        scheduleFeedItem.setShow(new Show());
        scheduleFeedItem.getShow().setId(15779);
        scheduleFeedItem.getShow().setUrl("http://www.tvmaze.com/shows/36765/girl-meets-farm");
        scheduleFeedItem.getShow().setName("Girl Meets Farm");
        scheduleFeedItem.getShow().setLanguage("English");
        scheduleFeedItem.getShow().setStatus("Running");
        assertEquals("Dinner Staycation", scheduleFeedItem.getEpisodeName());
        assertEquals("http://www.tvmaze.com/episodes/1953551/girl-meets-farm-6x10-dinner-staycation", scheduleFeedItem.getUrl());
        assertEquals("11:00", scheduleFeedItem.getAirtime());
        assertEquals(15779, scheduleFeedItem.getShow().getId());
        assertEquals("http://www.tvmaze.com/shows/36765/girl-meets-farm", scheduleFeedItem.getShow().getUrl());
        assertEquals("Girl Meets Farm", scheduleFeedItem.getShow().getName());
        assertEquals("English", scheduleFeedItem.getShow().getLanguage());
        assertEquals("Status: Running", scheduleFeedItem.getShow().getStatus());
    }
}
