package com.stara.enterprise.service.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import javax.annotation.PostConstruct;

public interface IFirebaseService {
    void initialize();

    Firestore getFirestore();

    UserRecord getUser(String uid) throws FirebaseAuthException;
}
