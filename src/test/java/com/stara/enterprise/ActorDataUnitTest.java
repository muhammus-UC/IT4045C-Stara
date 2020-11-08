package com.stara.enterprise;

import com.stara.enterprise.dto.actor.Actor;
import com.stara.enterprise.dto.actor.ActorCountry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ActorDataUnitTest {

    @Test
    void confirmBradleyCooper_outputsBradleyCooper() {
        Actor actor = new Actor();
        actor.setId(22012);
        actor.setUrl("http://www.tvmaze.com/people/22012/bradley-cooper");
        actor.setName("Bradley Cooper");
        actor.setCountry(new ActorCountry());
        actor.getCountry().setName("United States");
        actor.setGender("Male");
        assertEquals(22012, actor.getId());
        assertEquals("http://www.tvmaze.com/people/22012/bradley-cooper", actor.getUrl());
        assertEquals("Bradley Cooper", actor.getName());
        assertEquals("United States", actor.getCountry().getName());
        assertEquals("Male", actor.getGender());
    }

    @Test
    void confirmBradPitt_outputsBradPitt() {
        Actor actor = new Actor();
        actor.setId(45790);
        actor.setUrl("http://www.tvmaze.com/people/45790/brad-pitt");
        actor.setName("Brad Pitt");
        actor.setCountry(new ActorCountry());
        actor.getCountry().setName("United States");
        actor.setGender("Male");
        assertEquals(45790, actor.getId());
        assertEquals("http://www.tvmaze.com/people/45790/brad-pitt", actor.getUrl());
        assertEquals("Brad Pitt", actor.getName());
        assertEquals("United States", actor.getCountry().getName());
        assertEquals("Male", actor.getGender());
    }
}
