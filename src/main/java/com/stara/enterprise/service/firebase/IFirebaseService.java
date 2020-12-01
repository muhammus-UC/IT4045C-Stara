package com.stara.enterprise.service.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import javax.annotation.PostConstruct;

public interface IFirebaseService {
    /**
     * Used to initialize Service
     */
    void initialize();

    /**
     * Get reference to Firebase Firestore that stores Favorite data.
     *
     * @return Firestore object referencing Firestore where Favorite data is stored.
     */
    Firestore getFirestore();

    /**
     * Used to get user information about user with uid given.
     *
     * @param uid - uid of user to get user information for.
     * @return UserRecord object containing information about user, such as the user's display name or email.
     * @throws FirebaseAuthException thrown when error with Firebase Auth for any reason. Check error code & message for more details.
     */
    UserRecord getUser(String uid) throws FirebaseAuthException;
}
