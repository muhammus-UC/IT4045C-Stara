package com.stara.enterprise.service.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import javax.annotation.PostConstruct;

public interface IFirebaseService {
    // @PostConstruct - Initialize this class automatically once SpringBoot has finished starting
    @PostConstruct
    void initialize();

    Firestore getFirestore();

    UserRecord getUser(String uid) throws FirebaseAuthException;
}
