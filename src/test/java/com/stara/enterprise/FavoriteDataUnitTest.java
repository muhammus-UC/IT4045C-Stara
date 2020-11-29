package com.stara.enterprise;

import com.stara.enterprise.dto.Favorite;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit Tests for Favorite DTO only.
 * Not testing Service as it uses Firebase and testing that would require a lot of work beyond the scope of this course.
 */
@SpringBootTest
class FavoriteDataUnitTest {

    @Test
    void confirmFavoriteShow_outputsFavoriteShow() {
        Favorite favoriteShow = new Favorite();
        favoriteShow.setId("Show_42076");
        favoriteShow.setName("Transformers: Rescue Bots Academy");
        favoriteShow.setSubtitle("English");
        favoriteShow.setDetail("Running");
        favoriteShow.setImage("http://static.tvmaze.com/uploads/images/medium_portrait/194/487423.jpg");
        favoriteShow.setUrl("http://www.tvmaze.com/shows/42076/transformers-rescue-bots-academy");

        assertEquals("Show_42076", favoriteShow.getId());
        assertEquals("Transformers: Rescue Bots Academy", favoriteShow.getName());
        assertEquals("English", favoriteShow.getSubtitle());
        assertEquals("Running", favoriteShow.getDetail());
        assertEquals("http://static.tvmaze.com/uploads/images/medium_portrait/194/487423.jpg", favoriteShow.getImage());
        assertEquals("http://www.tvmaze.com/shows/42076/transformers-rescue-bots-academy", favoriteShow.getUrl());
    }

    @Test
    void confirmFavoriteActor_outputsFavoriteActor() {
        Favorite favoriteActor = new Favorite();
        favoriteActor.setId("Actor_56368");
        favoriteActor.setName("Dylan Moran");
        favoriteActor.setUrl("http://www.tvmaze.com/people/56368/dylan-moran");
        favoriteActor.setSubtitle("Male");
        favoriteActor.setDetail("Ireland");
        favoriteActor.setImage("http://static.tvmaze.com/uploads/images/medium_portrait/9/23040.jpg");
        favoriteActor.setUrl("http://www.tvmaze.com/people/56368/dylan-moran");

        assertEquals("Actor_56368", favoriteActor.getId());
        assertEquals("Dylan Moran", favoriteActor.getName());
        assertEquals("http://www.tvmaze.com/people/56368/dylan-moran", favoriteActor.getUrl());
        assertEquals("Male", favoriteActor.getSubtitle());
        assertEquals("Ireland", favoriteActor.getDetail());
        assertEquals("http://static.tvmaze.com/uploads/images/medium_portrait/9/23040.jpg", favoriteActor.getImage());
        assertEquals("http://www.tvmaze.com/people/56368/dylan-moran", favoriteActor.getUrl());
    }
}
