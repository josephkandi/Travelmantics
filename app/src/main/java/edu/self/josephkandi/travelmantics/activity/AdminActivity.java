package edu.self.josephkandi.travelmantics.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import edu.self.josephkandi.travelmantics.R;
import edu.self.josephkandi.travelmantics.models.Deal;

public class AdminActivity extends AppCompatActivity implements OnCompleteListener<UploadTask.TaskSnapshot> {

    private static final int REQUEST_CODE = 200;
    private static final String TAG = AdminActivity.class.getSimpleName();
    TextInputEditText textInputEditTextPlace;
    TextInputEditText textInputEditTextAmount;
    TextInputEditText textInputEditTextDescription;
    ImageView imageViewPlace;
    Uri placeImageUri;

    FirebaseFirestore firestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Deal currentDeal = new Deal();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        textInputEditTextPlace = findViewById(R.id.tiePlace);
        textInputEditTextAmount = findViewById(R.id.tieAmount);
        textInputEditTextDescription = findViewById(R.id.tieDescription);
        imageViewPlace = findViewById(R.id.imgPlace);
    }

    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{ "image/jpeg", "image/png"});
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            placeImageUri = data.getData();
            imageViewPlace.setImageURI(placeImageUri);
            storageReference = firebaseStorage.getReference("images");
            storageReference.putFile(placeImageUri).addOnCompleteListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.admin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveDeal();
                break;
        }
        return true;
    }

    private void saveDeal() {
        String place = textInputEditTextPlace.getText().toString();
        String amount =  textInputEditTextAmount.getText().toString();
        String description = textInputEditTextDescription.getText().toString();

        currentDeal.setPlace(place);
        currentDeal.setAmount(amount);
        currentDeal.setDescription(description);
        firestore.collection("deals")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("deals")
                .add(currentDeal);
    }



    @Override
    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
        Log.d(TAG, "Complete");
        if(task.isSuccessful()){
            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Log.d(TAG, task.getResult().toString());
                    currentDeal.setPlaceImageUrl(task.getResult().toString());

                }

            });
        } else {
            Toast.makeText (AdminActivity.this, "Unable to save image", Toast.LENGTH_SHORT).show();
        }
    }
}
