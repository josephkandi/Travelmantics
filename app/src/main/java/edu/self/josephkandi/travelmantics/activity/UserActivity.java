package edu.self.josephkandi.travelmantics.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import edu.self.josephkandi.travelmantics.R;
import edu.self.josephkandi.travelmantics.adapter.DealsAdapter;

public class UserActivity extends AppCompatActivity implements EventListener<QuerySnapshot> {
    RecyclerView recyclerView;
    DealsAdapter dealsAdapter = new DealsAdapter();
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firestore = FirebaseFirestore.getInstance();

        firestore.collection("deals").addSnapshotListener(this, this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dealsAdapter);
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
        
    }
}
