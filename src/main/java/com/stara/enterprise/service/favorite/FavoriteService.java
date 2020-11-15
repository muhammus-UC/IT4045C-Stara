package com.stara.enterprise.service.favorite;

import com.stara.enterprise.dao.favorite.IFavoriteDAO;
import com.stara.enterprise.dto.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FavoriteService implements IFavoriteService {
    @Autowired
    private IFavoriteDAO favoriteDAO;

    @Override
    public void delete(String email, String id) throws ExecutionException, InterruptedException {
        favoriteDAO.delete(email, id);
    }

    @Override
    public List<Favorite> fetchAll(String email) throws ExecutionException, InterruptedException {
        return favoriteDAO.fetchAll(email);
    }

    @Override
    public void save(Map<String, String> favoriteData, String email, String id) throws ExecutionException, InterruptedException {
        favoriteDAO.save(favoriteData, email, id);
    }
}
