package com.stara.enterprise.service.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Service used to interact with Firebase.
 * Firebase Auth is used for account management.
 * Firebase Firestore is used to store Favorites data in the cloud.
 *
 * References:
 * Made with help from fellow UC student Seth White's code (Github: sethwhite3)
 * https://medium.com/@prakash_chandra/crud-operations-in-springboot-using-firestore-f342e95e7802
 * https://artificialintelligence.oodles.io/dev-blogs/Expert-Guide-to-Integrating-Firebase-with-Springboot-Rest-API/
 */
@Service
@Profile("!test")
public class FirebaseService implements IFirebaseService{
    Logger log = LoggerFactory.getLogger(this.getClass());

    // Path to Firebase service account private key json - THIS IS SENSITIVE DATA AND MUST NOT BE SHARED FREELY!
    private final String serviceAccountPath = "src/main/java/com/stara/enterprise/service/firebase/stara-firebase-adminsdk.json";

    /**
     * Initializes FirebaseService
     * PostConstruct annotation tells SpringBoot to initialize this class automatically when SpringBoot runs
     */
    @PostConstruct
    @Override
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
            log.error("Unable to read service account file for Firebase Service initialization, a FileNotFoundException occurred. Message: " + e.getMessage(), e);

        } catch (IOException e) {
            log.error("Unable to store service credentials for Firebase Service initialization, an IOException occurred. Message: " + e.getMessage(), e);
        }
    }

    /**
     * Get reference to Firebase Firestore that stores Favorite data.
     * @return Firestore object referencing Firestore where Favorite data is stored.
     */
    @Override
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    /**
     * Used to get user information about user with uid given.
     * @param uid - uid of user to get user information for.
     * @return UserRecord object containing information about user, such as the user's display name or email.
     * @throws FirebaseAuthException thrown when error in accessing Firebase Auth for any reason.
     */
    @Override
    public UserRecord getUser(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);

        return userRecord;
    }
}
