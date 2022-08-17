package com.maruf.design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class login_activity extends AppCompatActivity {
    private EditText mEmail, mPass;
    private TextView mTextView;
    private Button signUpbtn;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        mEmail = findViewById(R.id.email_l);
        mPass = findViewById(R.id.passwd_l);
        mTextView=findViewById(R.id.text_signin);
        signUpbtn= findViewById(R.id.logup);

        mAuth = FirebaseAuth.getInstance();
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_activity.this, Activity_register.class));
            }
        });
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();

            }
        });
    }
    private void createUser(){
        String email= mEmail.getText().toString();
        String pass = mPass.getText().toString();
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pass.isEmpty()){
                mAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(login_activity.this, "User Registered Please verify your email", Toast.LENGTH_SHORT).show();
                                                mEmail.setText("");
                                                mPass.setText("");
                                            }else {
                                                Toast.makeText(login_activity.this, "gagal registered", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(login_activity.this, "Gagal registered", Toast.LENGTH_SHORT).show();
                                }




                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login_activity.this, "registered failure", Toast.LENGTH_SHORT).show();
                    }
                });

            }else {
                mPass.setError("error you know");
            }
        }else if(email.isEmpty()){
            mEmail.setError("empty email You know");
        }else {
            mEmail.setError("please Enter Correct Email");
        }

    }
}