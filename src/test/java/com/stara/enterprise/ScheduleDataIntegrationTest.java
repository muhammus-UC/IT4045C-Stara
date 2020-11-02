package com.stara.enterprise;

import com.stara.enterprise.dto.ScheduleFeedItem;
import com.stara.enterprise.dto.show.Show;
import com.stara.enterprise.service.schedule.IScheduleFeedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ScheduleDataIntegrationTest {

    @Autowired
    IScheduleFeedService scheduleFeedService;

    List<ScheduleFeedItem> scheduleFeed;

    @Test
    void scheduleDTO_containsShow() {
        givenViewModelIsInitialized();
        whenScheduleFeedDataAreReadAndParsed();
        thenScheduleFeedShouldContainShow();
    }

    private void andWhenSleep() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void givenViewModelIsInitialized() {
        assertNotNull(scheduleFeedService);
        scheduleFeed = null;
    }

    private void whenScheduleFeedDataAreReadAndParsed() {
        try {
            scheduleFeed = scheduleFeedService.fetchScheduleFeed("US");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void thenScheduleFeedShouldContainShow() {
        boolean showFound = false;

        for (ScheduleFeedItem scheduleFeedItem : scheduleFeed) {
            if (scheduleFeed.get(0).getShow() != null) {
                showFound = true;
                break;
            }
        }
        assertTrue(showFound);
    }
}
