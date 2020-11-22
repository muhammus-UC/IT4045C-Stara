package com.stara.enterprise.service.favorite;

import com.stara.enterprise.dto.Favorite;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Profile("test")
public class FavoriteServiceStub implements IFavoriteService {
    @Override
    public void delete(String email, String id) throws ExecutionException, InterruptedException {
        return;
    }

    @Override
    public List<Favorite> fetchAll(String email) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public void save(Map<String, String> favoriteData, String email, String id) throws ExecutionException, InterruptedException {
        return;
    }
}
