package edu.self.josephkandi.travelmantics.app;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class TravelmanticsApp extends Application {
    public FirebaseAuth firebaseAuth;
    public FirebaseFirestore firestore;
    public FirebaseStorage firebaseStorage;
    public FirebaseUser firebaseUser;

    @Override
    public void onCreate() {
        super.onCreate();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }
}
