package com.stara.enterprise;

import com.stara.enterprise.dao.actor.IActorFeedDAO;
import com.stara.enterprise.dto.ImageURL;
import com.stara.enterprise.dto.actor.Actor;
import com.stara.enterprise.dto.actor.ActorCountry;
import com.stara.enterprise.dto.actor.ActorFeedItem;
import com.stara.enterprise.service.actor.ActorFeedService;
import com.stara.enterprise.service.actor.IActorFeedService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActorDataUnitTest {
    private IActorFeedService actorFeedService;
    private List<ActorFeedItem> actorFeedItemListTested;

    @MockBean
    private IActorFeedDAO actorFeedDAO;

    @Test
    void confirmBradleyCooper_outputsBradleyCooper() {
        Actor actor = new Actor();
        actor.setId(22012);
        actor.setUrl("http://www.tvmaze.com/people/22012/bradley-cooper");
        actor.setName("Bradley Cooper");
        actor.setCountry(new ActorCountry("United States"));
        actor.setGender("Male");
        actor.setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/7/19608.jpg"));

        assertEquals(22012, actor.getId());
        assertEquals("http://www.tvmaze.com/people/22012/bradley-cooper", actor.getUrl());
        assertEquals("Bradley Cooper", actor.getName());
        assertEquals("United States", actor.getCountry().getName());
        assertEquals("Male", actor.getGender());
        assertEquals(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/7/19608.jpg"), actor.getImage());

        String stringBradleyCooper = "Actor Info - ID: 22012. " +
                "URL: http://www.tvmaze.com/people/22012/bradley-cooper. " +
                "Name: Bradley Cooper. Country: United States. Gender: Male. " +
                "Image URL: ImageURL(medium=http://static.tvmaze.com/uploads/images/medium_portrait/7/19608.jpg)";

        assertEquals(stringBradleyCooper, actor.toString());

        ActorFeedItem actorFeedItem = new ActorFeedItem();
        actorFeedItem.setScore(65.2);
        actorFeedItem.setActor(actor);

        assertEquals("ActorFeedItem - Score: 65.2. " + stringBradleyCooper, actorFeedItem.toString());
    }

    @Test
    void confirmBradPitt_outputsBradPitt() {
        Actor actor = new Actor();
        actor.setId(45790);
        actor.setUrl("http://www.tvmaze.com/people/45790/brad-pitt");
        actor.setName("Brad Pitt");
        actor.setCountry(new ActorCountry("United States"));
        actor.setGender("Male");
        actor.setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/11/29350.jpg"));

        assertEquals(45790, actor.getId());
        assertEquals("http://www.tvmaze.com/people/45790/brad-pitt", actor.getUrl());
        assertEquals("Brad Pitt", actor.getName());
        assertEquals("United States", actor.getCountry().getName());
        assertEquals("Male", actor.getGender());
        assertEquals(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/11/29350.jpg"), actor.getImage());

        String stringBradPitt = "Actor Info - ID: 45790. " +
                "URL: http://www.tvmaze.com/people/45790/brad-pitt. " +
                "Name: Brad Pitt. Country: United States. Gender: Male. " +
                "Image URL: ImageURL(medium=http://static.tvmaze.com/uploads/images/medium_portrait/11/29350.jpg)";

        assertEquals(stringBradPitt, actor.toString());

        ActorFeedItem actorFeedItem = new ActorFeedItem();
        actorFeedItem.setScore(75.7);
        actorFeedItem.setActor(actor);

        assertEquals("ActorFeedItem - Score: 75.7. " + stringBradPitt, actorFeedItem.toString());
    }

    // Confirms data which can be null from API is being handled properly.
    @Test
    void confirmNullActor_outputsNullActor() {
        Actor actor = new Actor();

        assertEquals(0, actor.getId());
        assertNull(actor.getUrl());
        assertNull(actor.getName());
        assertEquals(new ActorCountry("Country Unknown"), actor.getCountry());
        assertEquals("Gender Unknown", actor.getGender());
        assertEquals(new ImageURL("images/null.svg"), actor.getImage());

        assertEquals("Actor Info - ID: 0. URL: null. Name: null. Country: Country Unknown. Gender: Gender Unknown. Image URL: ImageURL(medium=images/null.svg)", actor.toString());
    }

    // Confirms Actor nested country data from API is being handled properly.
    @Test
    void confirmActorCountry_outputsActorCountry() {
        ActorCountry actorCountry1 = new ActorCountry();
        actorCountry1.setName("United Kingdom");
        assertEquals("United Kingdom", actorCountry1.getName());
        assertEquals("United Kingdom", actorCountry1.toString());

        ActorCountry actorCountry2 = new ActorCountry("United States");
        assertEquals("United States", actorCountry2.getName());
        assertEquals("United States", actorCountry2.toString());
    }

    private void givenActorDataAreAvailable() throws IOException {
        Actor actor1 = new Actor();
        actor1.setId(11615);
        actor1.setUrl("http://www.tvmaze.com/people/11615/joel-mchale");
        actor1.setName("Joel McHale");
        actor1.setCountry(new ActorCountry("Italy"));
        actor1.setGender("Male");
        actor1.setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/4/10878.jpg"));
        ActorFeedItem actorFeedItem1 = new ActorFeedItem();
        actorFeedItem1.setScore(50.0);
        actorFeedItem1.setActor(actor1);

        Actor actor2 = new Actor();
        actor2.setId(212615);
        actor2.setUrl("http://www.tvmaze.com/people/212615/joseph-barrios");
        actor2.setName("Joseph Barrios");
        actor2.setCountry(new ActorCountry("United States"));
        actor2.setGender("Male");
        actor2.setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/162/407084.jpg"));
        ActorFeedItem actorFeedItem2 = new ActorFeedItem();
        actorFeedItem2.setScore(25.0);
        actorFeedItem2.setActor(actor2);

        Actor actor3 = new Actor();
        actor3.setId(170621);
        actor3.setUrl("http://www.tvmaze.com/people/170621/joel-michael-kramer");
        actor3.setName("Joel Michale Kramer");
        actor3.setCountry(null);
        actor3.setGender("Male");
        actor3.setImage(new ImageURL("http://static.tvmaze.com/uploads/images/medium_portrait/195/488680.jpg"));
        ActorFeedItem actorFeedItem3 = new ActorFeedItem();
        actorFeedItem3.setScore(12.5);
        actorFeedItem3.setActor(actor3);

        List<ActorFeedItem> actorFeedItemListInitData = new ArrayList<ActorFeedItem>();
        actorFeedItemListInitData.add(actorFeedItem1);
        actorFeedItemListInitData.add(actorFeedItem2);
        actorFeedItemListInitData.add(actorFeedItem3);

        Mockito.when(actorFeedDAO.fetchActorFeed("Joel McHale")).thenReturn(actorFeedItemListInitData);
        actorFeedService = new ActorFeedService(actorFeedDAO);

        // Reinitialize variable to ensure data across tests doesn't cause false positives/negatives
        actorFeedItemListTested = new ArrayList<ActorFeedItem>();
    }

    @Test
    void searchForJoelMcHale_returnsJoelMcHale() throws IOException {
        givenActorDataAreAvailable();
        whenSearchForJoelMcHale();
        thenResultContainsJoelMcHale();
    }

    private void whenSearchForJoelMcHale() throws IOException {
        actorFeedItemListTested = actorFeedService.fetchActorFeed("Joel McHale");
    }

    private void thenResultContainsJoelMcHale() {
        boolean joelMcHaleFound = false;

        for (ActorFeedItem actorFeedItem : actorFeedItemListTested) {
            if (
                    actorFeedItem.getActor().getId() == 11615 &&
                            actorFeedItem.getActor().getUrl().equals("http://www.tvmaze.com/people/11615/joel-mchale") &&
                            actorFeedItem.getActor().getName().equals("Joel McHale") &&
                            actorFeedItem.getActor().getCountry().getName().equals("Italy") &&
                            actorFeedItem.getActor().getGender().equals("Male") &&
                            actorFeedItem.getActor().getImage().getMedium().equals("http://static.tvmaze.com/uploads/images/medium_portrait/4/10878.jpg") &&
                            actorFeedItem.getScore() == 50.0
            ) {
                joelMcHaleFound = true;
                break;
            }
        }

        assertTrue(joelMcHaleFound);
    }
}
