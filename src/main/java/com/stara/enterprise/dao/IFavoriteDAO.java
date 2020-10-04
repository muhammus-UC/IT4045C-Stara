package com.stara.enterprise.dao;

import com.stara.enterprise.dto.Favorite;

import java.util.List;

public interface IFavoriteDAO {
    void delete(int id);

    Favorite fetch(int id);

    List<Favorite> fetchAll();

    Favorite save(Favorite favorite) throws Exception;
}
