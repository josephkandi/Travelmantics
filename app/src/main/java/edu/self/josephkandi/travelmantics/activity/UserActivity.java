package edu.self.josephkandi.travelmantics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import edu.self.josephkandi.travelmantics.R;
import edu.self.josephkandi.travelmantics.adapter.DealsAdapter;
import edu.self.josephkandi.travelmantics.models.Deal;

public class UserActivity extends AppCompatActivity implements EventListener<QuerySnapshot> {
    private static final String TAG = UserActivity.class.getSimpleName();
    RecyclerView recyclerView;
    DealsAdapter dealsAdapter = new DealsAdapter();
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        firebaseAuth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("deals")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("deals")
                .addSnapshotListener(this, this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dealsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.user_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_deal:
                startActivity(new Intent(this, AdminActivity.class));
                break;
            case R.id.action_sign_out:
                signOut();
                break;
        }
        return true;
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
