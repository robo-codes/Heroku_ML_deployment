package com.example.project_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class registerUser extends AppCompatActivity {

    private Button registerUser;
    private EditText editTextTextEmailAddress4, editTextTextEmailAddress3, editTextTextEmailAddress2, editTextTextPassword2;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    Switch LogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.button2);
        registerUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button2:
                        registerUser();
                        break;
                }
            }
        });

        editTextTextEmailAddress4 = (EditText) findViewById(R.id.editTextTextEmailAddress4);
        editTextTextEmailAddress3 = (EditText) findViewById(R.id.editTextTextEmailAddress3);
        editTextTextEmailAddress2 = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        editTextTextPassword2 = (EditText) findViewById(R.id.editTextTextPassword2);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        LogIn = (Switch) findViewById((R.id.switch3));
        LogIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    startActivity(new Intent(registerUser.this, MainActivity.class));
                }
            }
        });
    }


    private void registerUser() {
        String email = editTextTextEmailAddress2.getText().toString().trim();
        String password = editTextTextPassword2.getText().toString().trim();
        String fullName = editTextTextEmailAddress4.getText().toString().trim();
        String PatientOrDoctor = editTextTextEmailAddress3.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextTextEmailAddress4.setError("Full name is required");
            editTextTextEmailAddress4.requestFocus();
            return;
        }
        if(PatientOrDoctor.isEmpty()) {
            editTextTextEmailAddress3.setError("Are you the doctor or the patient?");
            editTextTextEmailAddress3.requestFocus();
            return;
        }
        if(email.isEmpty()) {
            editTextTextEmailAddress2.setError("Please provide your email address !");
            editTextTextEmailAddress2.requestFocus();
            return;
        }
        if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            editTextTextEmailAddress2.setError("Please enter a valid Email !");
            editTextTextEmailAddress2.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            editTextTextPassword2.setError("Password is required");
            editTextTextPassword2.requestFocus();
            return;
        }
        if(password.length() < 6) {
            editTextTextPassword2.setError("Please enter more than 6 characters.");
            editTextTextPassword2.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user user = new user(fullName, PatientOrDoctor, email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(registerUser.this,"user has been registered successfully !", Toast.LENGTH_SHORT).show();
                                    }else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(registerUser.this,"failed to register! Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(registerUser.this, "failed to register! Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }
}