package edu.self.josephkandi.travelmantics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.self.josephkandi.travelmantics.R;

public class SignUpActivity extends BaseActivity {

    TextInputEditText textInputEditTextEmailAddress;
    TextInputEditText textInputEditTextFullName;
    TextInputEditText textInputEditTextPassword;
    Button btnSignUp;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = getApp().firebaseAuth;

        textInputEditTextEmailAddress = findViewById(R.id.tieEmail);
        textInputEditTextFullName = findViewById(R.id.tieFullName);
        textInputEditTextPassword = findViewById(R.id.tiePassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = textInputEditTextEmailAddress.getText().toString();
        String password = textInputEditTextPassword.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(SignUpActivity.this, AdminActivity.class));
                } else {
                    Toast.makeText(SignUpActivity.this, getString(R.string.sign_up_failed), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
