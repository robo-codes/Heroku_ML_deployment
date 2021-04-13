package com.example.project_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Switch SignUp;
    private TextView ForgotPassword;
    private EditText editTextTextEmailAddress, editTextTextPassword;
    private Button Login;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignUp = (Switch) findViewById((R.id.switch1));
        SignUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true) {
                    startActivity(new Intent(MainActivity.this, registerUser.class));
                }
            }
        });

        Login = (Button) findViewById(R.id.button);
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button:
                        userLogin();
                        break;
                }
            }
        });

        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        ForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.textViewForgotPassword:
                        startActivity(new Intent(MainActivity.this, forgotPassword.class));
                        break;
                }
            }
        });

    }

    

    private void userLogin() {
        String email = editTextTextEmailAddress.getText().toString().trim();
        String password = editTextTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextTextEmailAddress.setError("Email is required.");
            editTextTextEmailAddress.requestFocus();
            return;
        }
        if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            editTextTextEmailAddress.setError("Please enter a valid email address !");
            editTextTextEmailAddress.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextTextPassword.setError("Password is required.");
            editTextTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextTextPassword.setError("minimum 6 characters are required !");
            editTextTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()) {
                        startActivity(new Intent(MainActivity.this, HomePage.class));
                    }
                    else {
                        user.sendEmailVerification();
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this,"check your email and verify it!",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this,"failed to login! Please check your credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}