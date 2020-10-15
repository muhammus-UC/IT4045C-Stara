package com.stara.enterprise;

import com.stara.enterprise.dto.show.Show;
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
    void confirmGoodPlace_outputsGoodPlace() {
        Show show = new Show();
        show.setId(2790);
        show.setName("The Good Place");
        show.setLanguage("English");
        show.setStatus("Ended");
        assertEquals(2790, show.getId());
        assertEquals("English", show.getLanguage());
        assertEquals("Ended", show.getStatus());
    }

}
