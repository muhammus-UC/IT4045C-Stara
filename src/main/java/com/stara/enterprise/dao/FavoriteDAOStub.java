package com.stara.enterprise.dao;

import com.stara.enterprise.dto.Favorite;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FavoriteDAOStub implements IFavoriteDAO {
    Map<Integer, Favorite> allFavorites = new HashMap<>();

    @Override
    public void delete(int id) {
        allFavorites.remove(id);
    }

    @Override
    public Favorite fetch(int id) {
        return allFavorites.get(id);
    }

    @Override
    public List<Favorite> fetchAll() {
        List<Favorite> returnFavorites = new ArrayList(allFavorites.values());
        return returnFavorites;
    }

    @Override
    public Favorite save(Favorite favorite) throws Exception {
        Integer favoriteID = favorite.getId();
        allFavorites.put(favoriteID, favorite);
        return favorite;
    }
}
