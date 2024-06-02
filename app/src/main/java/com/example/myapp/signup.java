package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    EditText mfullname, memail, mpwd, mphone, mnumbat;
    Button msignupbtn;
    Button loginbtn;

    FirebaseAuth fauth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mfullname = findViewById(R.id.fullName);
        memail = findViewById(R.id.Email);
        mpwd = findViewById(R.id.Password);
        mphone = findViewById(R.id.Phone);
        mnumbat = findViewById(R.id.Numbat);
        msignupbtn = findViewById(R.id.signupbtn);
        loginbtn = findViewById(R.id.login);

        fauth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        msignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = mfullname.getText().toString().trim();
                String email = memail.getText().toString().trim();
                String passwd = mpwd.getText().toString().trim();
                String phone = mphone.getText().toString().trim();
                String numbat = mnumbat.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(signup.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwd)) {
                    Toast.makeText(signup.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwd.length() < 6) {
                    mpwd.setError("Password must be >= 6 characters");
                    return;
                }

                // Create the user in Firebase Authentication
                fauth.createUserWithEmailAndPassword(email, passwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(signup.this, "Account created", Toast.LENGTH_SHORT).show();

                                    // Get the user ID and store additional user information in Firestore
                                    FirebaseUser user = fauth.getCurrentUser();
                                    DocumentReference docRef = fStore.collection("users").document(user.getUid());

                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("fullName", fullName);
                                    userInfo.put("email", email);
                                    userInfo.put("phone", phone);
                                    userInfo.put("numbat", numbat);

                                    docRef.set(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(signup.this, "User data saved", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(signup.this, "Error saving user data", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    // Redirect to login activity
                                    Intent intent = new Intent(getApplicationContext(), login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
    }
}
