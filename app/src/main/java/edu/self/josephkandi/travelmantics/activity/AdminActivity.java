package edu.self.josephkandi.travelmantics.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import edu.self.josephkandi.travelmantics.R;
import edu.self.josephkandi.travelmantics.app.TravelmanticsApp;
import edu.self.josephkandi.travelmantics.models.Deal;
import edu.self.josephkandi.travelmantics.utils.Constants;

public class AdminActivity extends AppCompatActivity implements OnCompleteListener<UploadTask.TaskSnapshot> {

    private static final int REQUEST_CODE = 200;
    private static final String TAG = AdminActivity.class.getSimpleName();
    TextInputEditText textInputEditTextPlace;
    TextInputEditText textInputEditTextAmount;
    TextInputEditText textInputEditTextDescription;
    ImageView imageViewPlace;
    Uri placeImageUri;
    ProgressBar progressBar;
    TravelmanticsApp app;

    StorageReference storageReference;
    Deal currentDeal = new Deal();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        app = (TravelmanticsApp)getApplication();


        textInputEditTextPlace = findViewById(R.id.tiePlace);
        textInputEditTextAmount = findViewById(R.id.tieAmount);
        textInputEditTextDescription = findViewById(R.id.tieDescription);
        imageViewPlace = findViewById(R.id.imgPlace);
        progressBar = findViewById(R.id.pbProgress);
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
            storageReference = app.firebaseStorage.getReference().child("images/" + placeImageUri.getLastPathSegment());
            storageReference.putFile(placeImageUri).addOnCompleteListener(this);
            progressBar.setVisibility(View.VISIBLE);
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


        AlertDialog progressDialog = new AlertDialog.Builder(this)

                .create();

        currentDeal.setPlace(place);
        currentDeal.setAmount(amount);
        currentDeal.setDescription(description);
        app.firestore.collection(Constants.DEALS_COLLECTION)
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection(Constants.DEALS_COLLECTION)
                .add(currentDeal)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        finish();
                    }
                });
    }



    @Override
    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
        Log.d(TAG, "Upload finished");
        progressBar.setVisibility(View.INVISIBLE);
        if(task.isSuccessful()){
            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    currentDeal.setPlaceImageUrl(task.getResult().toString());
                    Log.d(TAG, task.getResult().toString());
                }

            });
        } else {
            Toast.makeText (AdminActivity.this, getString(R.string.image_upload_failed), Toast.LENGTH_SHORT).show();
        }
    }
}
