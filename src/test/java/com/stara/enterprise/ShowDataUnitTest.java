package com.stara.enterprise;

import com.stara.enterprise.dto.Show;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShowDataUnitTest {

    @Test
    void confirmNailedIt_outputsNailedIt() {
        Show show = new Show();
        show.setId(35093);
        show.setName("Nailed It!");
        show.setLanguage("English");
        show.setStatus("To Be Determined");
        assertEquals(35093, show.getId());
        assertEquals("English", show.getLanguage());
        assertEquals("To Be Determined", show.getStatus());
    }
    
    @Test
    void confirmNailedIt_outputsNailedIt() {
        Show show = new Show();
        show.setId(35094);
        show.setName("The Good Place");
        show.setLanguage("English");
        show.setStatus("To Be Determined");
        assertEquals(35094, show.getId());
        assertEquals("English", show.getLanguage());
        assertEquals("To Be Determined", show.getStatus());
    }

}
