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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

 public class Activity_register extends AppCompatActivity {
    private EditText mEmail, mPass;
    private TextView mTextView;
    private Button signInbtn;
    private FirebaseAuth.AuthStateListener listener;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.email_s);
        mPass = findViewById(R.id.passwd_s);
        mTextView=findViewById(R.id.text_signup);
        signInbtn= findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_register.this, login_activity.class));
            }
        });
        signInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();

            }
        });

        listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user !=null){
                    if (user.isEmailVerified()){
                        startActivity(new Intent(Activity_register.this, ListData.class));
                        finish();

                    }else {
                        Toast.makeText(Activity_register.this, "", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        };
    }

    private void loginUser(){
        String email= mEmail.getText().toString();
        String pass = mPass.getText().toString();
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pass.isEmpty()){
                mAuth.signInWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                   if (mAuth.getCurrentUser().isEmailVerified()){
                                       startActivity(new Intent(Activity_register.this, ListData.class));
                                   }else if (!mAuth.getCurrentUser().isEmailVerified()){
                                       Toast.makeText(Activity_register.this, "gagal, please verify your email", Toast.LENGTH_SHORT).show();
                                   }
                                }else{
                                    Toast.makeText(Activity_register.this, "failed creat your accout", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Activity_register.this, "gagal login", Toast.LENGTH_SHORT).show();

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

     @Override
     protected void onStart() {
         super.onStart();
         mAuth.addAuthStateListener(listener);
     }

     @Override
     protected void onStop() {
         super.onStop();
         if(listener != null ){
             mAuth.removeAuthStateListener(listener);
         }
     }
 }