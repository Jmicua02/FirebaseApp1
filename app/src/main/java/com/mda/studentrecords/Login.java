package com.mda.studentrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class Login extends AppCompatActivity {

    EditText logEmail, logPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logEmail= findViewById(R.id.editEmail);
        logPassword= findViewById(R.id.editPassword);

        mAuth=FirebaseAuth.getInstance();
    }

    public void createAccount(View view) {
        Intent intent= new Intent(getApplicationContext(), register.class);
        startActivity(intent);
    }

    public void logAccount(View view) {

        try {
            String email, password;

            email= logEmail.getText().toString().trim();
            password= logPassword.getText().toString().trim();

            if (email.isEmpty()||password.isEmpty()){
                Snackbar.make(view, "Email and Password are required.", Snackbar.LENGTH_LONG).show();
            }

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Intent intent = new Intent(Login.this, profile.class);
                                startActivity(intent);
                            } else {
                                Snackbar.make(view, "Login Failed. Please check credentials", Snackbar.LENGTH_LONG).show();
                            }


                        }
                    });
        }catch (Exception e){
            Snackbar.make(view, "Check your internet connection", Snackbar.LENGTH_LONG).show();
        }






    }
}