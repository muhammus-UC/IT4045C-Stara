package com.stara.enterprise.service;

import com.stara.enterprise.dao.IFavoriteDAO;
import com.stara.enterprise.dto.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceStub implements IFavoriteService {
    @Autowired
    private IFavoriteDAO favoriteDAO;

    public FavoriteServiceStub() {}

    public FavoriteServiceStub(IFavoriteDAO favoriteDAO) {
        this.favoriteDAO = favoriteDAO;
    }

    @Override
    @CacheEvict(value="favorite", key="#id")
    public void delete(int id){
        favoriteDAO.delete(id);
    }

    @Override
    @Cacheable(value="favorite", key="#id")
    public Favorite fetchById(int id) {
        Favorite foundFavorite = favoriteDAO.fetch(id);
        return foundFavorite;
    }

    @Override
    @Cacheable("favorites")
    public List<Favorite> fetchAll() {
        return favoriteDAO.fetchAll();
    }

    @Override
    public Favorite save(Favorite favorite) throws Exception {
        return favoriteDAO.save(favorite);
    }
}
