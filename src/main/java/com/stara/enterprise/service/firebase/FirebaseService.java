package com.stara.enterprise.service.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * References
 * https://medium.com/@prakash_chandra/crud-operations-in-springboot-using-firestore-f342e95e7802
 * https://artificialintelligence.oodles.io/dev-blogs/Expert-Guide-to-Integrating-Firebase-with-Springboot-Rest-API/
 * Also got help from fellow UC student Seth White's code (Github: sethwhite3)
 */

@Service
public class FirebaseService {
    // Path to Firebase service account private key json
    private final String serviceAccountPath = "src/main/java/com/stara/enterprise/service/firebase/stara-firebase-adminsdk.json";

    // @PostConstruct - Initialize this class automatically once SpringBoot has finished starting
    @PostConstruct
    public void initialize() {
        try {
            // Store service account credentials
            FileInputStream serviceAccountFile = new FileInputStream(serviceAccountPath);
            GoogleCredentials serviceCredentials = GoogleCredentials.fromStream(serviceAccountFile);

            // Configure Firebase for Stara database
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(serviceCredentials)
                    .setDatabaseUrl("https://stara-it3048c.firebaseio.com")
                    .build();

            // Initialize Firebase for Stara
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    public UserRecord getUser(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);

        System.out.println("Got user with email of " + userRecord.getEmail());

        return userRecord;
    }
}
