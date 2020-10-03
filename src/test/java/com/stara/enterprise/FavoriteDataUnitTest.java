package com.stara.enterprise;

import com.stara.enterprise.dto.Favorite;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FavoriteDataUnitTest {

    @Test
    void confirmFavorite_outputsFavorite() {
        Favorite favorite = new Favorite();
        favorite.setId(42076);
        favorite.setName("Transformers: Rescue Bots Academy");
        favorite.setSubtitle("English");
        favorite.setDetail("Running");
        assertEquals(42076, favorite.getId());
        assertEquals("Transformers: Rescue Bots Academy", favorite.getName());
        assertEquals("English", favorite.getSubtitle());
        assertEquals("Running", favorite.getDetail());
    }

}
