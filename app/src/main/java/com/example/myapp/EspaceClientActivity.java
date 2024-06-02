package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EspaceClientActivity extends AppCompatActivity {

    private static final String TAG = "EspaceClientActivity";

    private EditText usernameEditText, phoneEditText, orderDateEditText, reclamationEditText;
    private Button submitReclamationButton;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espaceclient);

        // Find views
        usernameEditText = findViewById(R.id.et_username);
        phoneEditText = findViewById(R.id.et_phone);
        orderDateEditText = findViewById(R.id.et_order_date);
        reclamationEditText = findViewById(R.id.et_reclamation_description);
        submitReclamationButton = findViewById(R.id.btn_submit_reclamation);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Set click listener for submit button
        submitReclamationButton.setOnClickListener(v -> submitReclamation());

        // Disable submit button initially
        submitReclamationButton.setEnabled(false);

        // Add text change listeners to enable/disable submit button based on field validation
        TextWatcher validationTextWatcher = new ValidationTextWatcher();
        usernameEditText.addTextChangedListener(validationTextWatcher);
        phoneEditText.addTextChangedListener(validationTextWatcher);
        orderDateEditText.addTextChangedListener(validationTextWatcher);
        reclamationEditText.addTextChangedListener(validationTextWatcher);

        // Add text watcher to format date
        orderDateEditText.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final String ddmmyyyy = "DDMMYY";
            private final Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Date formatting logic
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set hint for order date EditText
        orderDateEditText.setHint("DD/MM/YY");
    }

    private void submitReclamation() {
        String username = usernameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String orderDate = orderDateEditText.getText().toString().trim();
        String reclamationDescription = reclamationEditText.getText().toString().trim();

        Map<String, Object> reclamation = new HashMap<>();
        reclamation.put("username", username);
        reclamation.put("phone", phone);
        reclamation.put("orderDate", orderDate);
        reclamation.put("reclamationDescription", reclamationDescription);

        firestore.collection("reclamations")
                .add(reclamation)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(EspaceClientActivity.this, "Reclamation submitted successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EspaceClientActivity.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error saving reclamation data: ", e);
                    Toast.makeText(EspaceClientActivity.this, "Failed to submit reclamation: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private class ValidationTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validateFields();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    private void validateFields() {
        String username = usernameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String orderDate = orderDateEditText.getText().toString().trim();
        String reclamationDescription = reclamationEditText.getText().toString().trim();

        boolean isValid = !username.isEmpty() && !phone.isEmpty() && !orderDate.isEmpty() && !reclamationDescription.isEmpty();
        submitReclamationButton.setEnabled(isValid);
    }
}
