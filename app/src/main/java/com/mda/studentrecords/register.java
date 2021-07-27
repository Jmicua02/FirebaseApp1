package com.mda.studentrecords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class register extends AppCompatActivity {

    EditText regIDNumber, regFname, regLname, regEmail, regPassword;
    RadioGroup userType;
    RadioButton rbInstructor, rbStudent;



    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regIDNumber= findViewById(R.id.regIdNumber);
        regFname= findViewById(R.id.regFname);
        regLname= findViewById(R.id.regLname);
        regEmail= findViewById(R.id.regEmail);
        regPassword= findViewById(R.id.regPassword);
        userType= findViewById(R.id.userType);
        rbInstructor= findViewById(R.id.rbInstructor);
        rbStudent= findViewById(R.id.rbStudent);

        mAuth= FirebaseAuth.getInstance();
    }

    public void registerFunc(View view) {
        String idNumber, firstName, lastName, email, password;
        int userType;

        idNumber= regIDNumber.getText().toString();
        firstName= regFname.getText().toString().trim();
        lastName= regLname.getText().toString().trim();
        email= regEmail.getText().toString().trim();
        password= regPassword.getText().toString().trim();

       if(rbInstructor.isChecked()){
           userType=0;
       }else{
           userType=1;
       }


        if (idNumber.isEmpty()||firstName.isEmpty()||lastName.isEmpty()||email.isEmpty()||password.isEmpty()){
            Snackbar.make(view, "Please fill up all the fields!", Snackbar.LENGTH_SHORT).show();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Snackbar.make(view, "Please enter a valid email address!", Snackbar.LENGTH_SHORT).show();
        }else {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                User user = new User(idNumber, firstName, lastName, email, userType);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Snackbar.make(view, "You have successfully registered!", Snackbar.LENGTH_LONG).show();
                                            Intent intent= new Intent(getApplicationContext(), Login.class);
                                            startActivity(intent);

                                        }else {
                                            Snackbar.make(view, "Registration unsuccessful! Please try again.2", Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Snackbar.make(view, "Registration unsuccessful! Please try again.", Snackbar.LENGTH_SHORT).show();
                            }


                        }
                    });
        }





    }
}