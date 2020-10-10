package com.stara.enterprise;

import com.stara.enterprise.dao.favorite.IFavoriteDAO;
import com.stara.enterprise.dto.Favorite;
import com.stara.enterprise.service.FavoriteServiceStub;
import com.stara.enterprise.service.IFavoriteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
class FavoriteDataUnitTest {

    private IFavoriteService favoriteService;
    private Favorite favorite = new Favorite();

    @MockBean
    private IFavoriteDAO favoriteDAO;

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

    @Test void fetchFavoriteByID_returnsFavoriteForID() throws Exception {
        givenFavoriteDataAreAvailable();
        whenFavorite318AddedIsCommunity();
        whenSearchFavoriteWithID318();
        thenReturnCommunityForID318();
    }

    private void givenFavoriteDataAreAvailable() throws Exception {
        Mockito.when(favoriteDAO.save(favorite)).thenReturn(favorite);
        favoriteService = new FavoriteServiceStub(favoriteDAO);
    }

    private void whenFavorite318AddedIsCommunity() {
        Favorite community = new Favorite();
        community.setId(318);
        community.setName("Community");
        community.setSubtitle("English");
        community.setDetail("Ended");
        Mockito.when(favoriteDAO.fetch(318)).thenReturn(community);
    }

    private void whenSearchFavoriteWithID318() {
        favorite = favoriteService.fetchById(318);
    }

    private void thenReturnCommunityForID318() {
        String name = favorite.getName();
        assertEquals("Community", name);
    }

    @Test
    void saveFavorite_validateReturnFavorite() throws Exception {
        givenFavoriteDataAreAvailable();
        whenUserCreatesANewFavoriteAndSaves();
        thenCreateNewFavoriteRecordAndReturnIt();
    }

    private void whenUserCreatesANewFavoriteAndSaves() {
        favorite.setId(318);
        favorite.setName("Community");
        favorite.setSubtitle("English");
        favorite.setDetail("Ended");
    }

    private void thenCreateNewFavoriteRecordAndReturnIt() throws Exception {
        Favorite createdFavorite = favoriteService.save(favorite);
        assertEquals(favorite, createdFavorite);
        verify(favoriteDAO, atLeastOnce()).save(favorite);
    }

}
