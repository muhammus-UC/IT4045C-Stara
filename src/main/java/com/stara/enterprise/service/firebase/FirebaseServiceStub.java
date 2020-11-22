package com.stara.enterprise.service.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class FirebaseServiceStub implements IFirebaseService {
    @Override
    public void initialize() {
        return;
    }

    @Override
    public Firestore getFirestore() {
        return null;
    }

    @Override
    public UserRecord getUser(String uid) throws FirebaseAuthException {
        return null;
    }
}
