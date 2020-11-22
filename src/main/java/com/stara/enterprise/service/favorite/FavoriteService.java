package com.stara.enterprise.service.favorite;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.stara.enterprise.dto.Favorite;
import com.stara.enterprise.service.firebase.FirebaseService;
import com.stara.enterprise.service.firebase.IFirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FavoriteService implements IFavoriteService {
    @Autowired
    IFirebaseService firebaseService;

    private final String fireStoreCollectionUsers = "users";
    private final String fireStoreCollectionFavorites = "favorites";

    // Firebase Reference: https://firebase.google.com/docs/firestore/manage-data/delete-data
    @Override
    public void delete(String email, String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = firebaseService.getFirestore()
                .collection(fireStoreCollectionUsers)
                .document(email)
                .collection(fireStoreCollectionFavorites)
                .document(id)
                .delete();

        System.out.println("Favorite with " + id + "removed at time: " + writeResult.get().getUpdateTime());
    }

    // Firebase Reference: https://firebase.google.com/docs/firestore/query-data/get-data
    @Override
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

    // Firebase Reference: https://firebase.google.com/docs/firestore/manage-data/add-data
    @Override
    public void save(Map<String, String> favoriteData, String email, String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = firebaseService.getFirestore()
                .collection(fireStoreCollectionUsers)
                .document(email)
                .collection(fireStoreCollectionFavorites)
                .document(id)
                .set(favoriteData);

        System.out.println("Favorite with " + id + "added at time: " + writeResult.get().getUpdateTime());
    }
}
