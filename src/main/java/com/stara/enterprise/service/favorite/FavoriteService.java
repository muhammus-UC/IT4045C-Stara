package com.stara.enterprise.service.favorite;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.stara.enterprise.dto.Favorite;
import com.stara.enterprise.service.firebase.FirebaseService;
import com.stara.enterprise.service.firebase.IFirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Service that uses Firebase Service to read & write Favorite data to Firestore.
 */
@Service
public class FavoriteService implements IFavoriteService {
    @Autowired
    IFirebaseService firebaseService;

    // Firestore collections that are used to store Favorite data.
    // Favorite Data is stored in the following path: fireStoreCollectionUsers/emailOfUser/fireStoreCollectionFavorites/favoriteId
    private static final String fireStoreCollectionUsers = "users";
    private static final String fireStoreCollectionFavorites = "favorites";

    // Firebase Reference: https://firebase.google.com/docs/firestore/manage-data/delete-data

    /**
     * Delete Favorite in Firestore for email and id specified.
     * Firebase Reference on deleting data: https://firebase.google.com/docs/firestore/manage-data/delete-data
     *
     * @param email of user who to delete Favorite for
     * @param id of Favorite to delete
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    @CacheEvict(value = "favorites", allEntries = true)
    public void delete(String email, String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = firebaseService.getFirestore()
                .collection(fireStoreCollectionUsers)
                .document(email)
                .collection(fireStoreCollectionFavorites)
                .document(id)
                .delete();
    }

    /**
     * Get Favorites from Firestore for user by email specified.
     * Firebase Reference on getting data: https://firebase.google.com/docs/firestore/query-data/get-data
     *
     * @param email of user who to retrieve Favorite(s) for
     *
     * @return List<Favorite> containing all favorites that belong to user
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    @Cacheable(value = "favorites")
    public List<Favorite> fetchAll(String email) throws ExecutionException, InterruptedException {
        List<Favorite> allFavorites = new ArrayList<>();

        ApiFuture<QuerySnapshot> querySnapshot = firebaseService.getFirestore()
                .collection(fireStoreCollectionUsers)
                .document(email)
                .collection(fireStoreCollectionFavorites)
                .get();

        List<QueryDocumentSnapshot> documents = null;
        documents = querySnapshot.get().getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            allFavorites.add(document.toObject(Favorite.class));
        }

        return allFavorites;
    }


    /**
     * Create Favorite in Firestore for email and id specified.
     * Firebase Reference on adding data: https://firebase.google.com/docs/firestore/manage-data/add-data
     *
     * @param favoriteData Map<String, String> object containing the Favorite data to write.
     * @param email of user who to create Favorite for
     * @param id of Favorite to create
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    @CacheEvict(value = "favorites", allEntries = true)
    public void save(Map<String, String> favoriteData, String email, String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = firebaseService.getFirestore()
                .collection(fireStoreCollectionUsers)
                .document(email)
                .collection(fireStoreCollectionFavorites)
                .document(id)
                .set(favoriteData);
    }
}
