package com.stara.enterprise.service;

import com.stara.enterprise.dto.Favorite;

import java.util.List;

public interface IFavoriteService {
    void delete(int id) throws Exception;

    Favorite fetchById(int id);

    List<Favorite> fetchAll();

    Favorite save(Favorite favorite) throws Exception;
}
