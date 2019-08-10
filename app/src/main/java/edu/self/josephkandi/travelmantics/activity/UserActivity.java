package edu.self.josephkandi.travelmantics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import edu.self.josephkandi.travelmantics.R;
import edu.self.josephkandi.travelmantics.adapter.DealsAdapter;
import edu.self.josephkandi.travelmantics.app.TravelmanticsApp;
import edu.self.josephkandi.travelmantics.models.Deal;
import edu.self.josephkandi.travelmantics.utils.Constants;

public class UserActivity extends BaseActivity implements EventListener<QuerySnapshot> {
    private static final String TAG = UserActivity.class.getSimpleName();
    RecyclerView recyclerView;
    TravelmanticsApp app;
    DealsAdapter dealsAdapter = new DealsAdapter();
    FloatingActionButton fab;
    private ListenerRegistration unSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        app = getApp();


        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this, AdminActivity.class));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(dealsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        app.firestore = FirebaseFirestore.getInstance();

        unSubscribe = app.firestore.collection(Constants.DEALS_COLLECTION)
                .document(app.firebaseAuth.getCurrentUser().getUid())
                .collection(Constants.DEALS_COLLECTION)
                .addSnapshotListener(this, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unSubscribe.remove();
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

        for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){

            if(documentChange.getType() == DocumentChange.Type.ADDED){
                QueryDocumentSnapshot document = documentChange.getDocument();
                Deal deal = document.toObject(Deal.class);
                deal.setId(document.getId());
                dealsAdapter.addDeal(deal);
                Log.d(TAG, deal.toString());
            }
        }
    }
}
