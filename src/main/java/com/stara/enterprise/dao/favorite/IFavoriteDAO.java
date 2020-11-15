package com.stara.enterprise.dao.favorite;

import com.stara.enterprise.dto.Favorite;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IFavoriteDAO {
    void delete(String email, String id) throws ExecutionException, InterruptedException;

    List<Favorite> fetchAll(String email) throws ExecutionException, InterruptedException;

    void save(Map<String, String> favoriteData, String email, String id) throws ExecutionException, InterruptedException;
}
