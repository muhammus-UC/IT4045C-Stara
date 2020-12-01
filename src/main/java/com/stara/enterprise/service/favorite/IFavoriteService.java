package com.stara.enterprise.service.favorite;

import com.stara.enterprise.dto.Favorite;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface IFavoriteService {
    void delete(String email, String id);

    List<Favorite> fetchAll(String email) throws ExecutionException, InterruptedException;

    void save(Map<String, String> favoriteData, String email, String id);
}
