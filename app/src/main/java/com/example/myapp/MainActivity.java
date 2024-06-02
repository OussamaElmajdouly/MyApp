package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signOutButton, histoireButton,mapsButton, employeesButton, aboutusButton, espaceClientButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Find buttons
        signOutButton = findViewById(R.id.sign_out_button);
        mapsButton = findViewById(R.id.maps_button);
        employeesButton = findViewById(R.id.employees_button);
        aboutusButton = findViewById(R.id.about_us_button);
        espaceClientButton = findViewById(R.id.espace_btn);
        histoireButton = findViewById(R.id.histoire_btn);

        // Set click listener for sign-out button
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, login.class));
                finish(); // Finish MainActivity to prevent the user from coming back here
            }
        });

        // Set click listener for maps button
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MapActivity
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });
        histoireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MapActivity
                startActivity(new Intent(MainActivity.this, Histoire.class));
            }
        });
        aboutusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MapActivity
                startActivity(new Intent(MainActivity.this, aboutus.class));
            }
        });

        // Set click listener for employees button
        employeesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Employees activity
                startActivity(new Intent(MainActivity.this, Employees.class));
            }
        });

        // Set click listener for reclamation button


        // Set click listener for espace client button
        espaceClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the EspaceClientActivity
                startActivity(new Intent(MainActivity.this, EspaceClientActivity.class));
            }
        });
    }
}
