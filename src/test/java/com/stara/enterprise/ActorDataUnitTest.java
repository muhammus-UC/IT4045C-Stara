package com.stara.enterprise;

import com.stara.enterprise.dto.Actor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ActorDataUnitTest {

    @Test
    void confirmBradleyCooper_outputsBradleyCooper() {
        Actor actor = new Actor();
        actor.setId(22012);
        actor.setName("Bradley Cooper");
        actor.setCountry("United States");
        actor.setGender("Male");
        assertEquals(22012, actor.getId());
        assertEquals("Bradley Cooper", actor.getName());
        assertEquals("United States", actor.getCountry());
        assertEquals("Male", actor.getGender());
    }

}
