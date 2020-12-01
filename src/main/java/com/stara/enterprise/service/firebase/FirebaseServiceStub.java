package com.stara.enterprise.service.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.UserRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Empty stub implementation of IFirebaseService to make tests pass on CircleCI.
 * Meaning all the methods do nothing or return null, as such no comments are needed for them.
 */
@Service
@Profile("test")
public class FirebaseServiceStub implements IFirebaseService {
    @Override
    public void initialize() {
    }

    @Override
    public Firestore getFirestore() {
        return null;
    }

    @Override
    public UserRecord getUser(String uid) {
        return null;
    }
}
