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

public class EmailLoginActivity extends BaseActivity {

    TextInputEditText textInputEditTextEmailAddress;
    TextInputEditText textInputEditTextPassword;
    Button btnLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        firebaseAuth = getApp().firebaseAuth;

        textInputEditTextEmailAddress = findViewById(R.id.tieEmailAddress);
        textInputEditTextPassword = findViewById(R.id.tiePassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin();
            }
        });
    }

    private void onLogin() {
        String email = textInputEditTextEmailAddress.getText().toString();
        String password = textInputEditTextPassword.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(EmailLoginActivity.this, AdminActivity.class));
                } else {
                    Toast.makeText(EmailLoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
