package edu.self.josephkandi.travelmantics.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import edu.self.josephkandi.travelmantics.R;
import edu.self.josephkandi.travelmantics.adapter.DealsAdapter;
import edu.self.josephkandi.travelmantics.models.Deal;

public class UserActivity extends AppCompatActivity implements EventListener<QuerySnapshot> {
    private static final String TAG = UserActivity.class.getSimpleName();
    RecyclerView recyclerView;
    DealsAdapter dealsAdapter = new DealsAdapter();
    FirebaseFirestore firestore;
    ArrayList<Deal> deals = new ArrayList<>();

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
        if(e != null) {
            Log.w(TAG, "Error " + e.getMessage());
        }

        for (DocumentChange document : queryDocumentSnapshots.getDocumentChanges()){
            if(document.getType() == DocumentChange.Type.ADDED){
                Deal deal = document.getDocument().toObject(Deal.class);
                dealsAdapter.addDeal(deal);
                Log.d(TAG, deal.toString());
            }
        }
    }
}
