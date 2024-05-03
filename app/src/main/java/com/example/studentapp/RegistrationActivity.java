package com.example.studentapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText rollEditText, nameEditText, courseEditText, branchEditText, yearEditText, semesterEditText, sectionEditText;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        // Initialize FirebaseDatabase with custom URL
        String firebaseUrl = "https://studentapp-abfd2-default-rtdb.firebaseio.com/";
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(firebaseUrl).child("users");

        // Initialize EditText fields
        rollEditText = findViewById(R.id.rollEditText);
        nameEditText = findViewById(R.id.nameEditText);
        courseEditText = findViewById(R.id.courseEditText);
        branchEditText = findViewById(R.id.branchEditText);
        yearEditText = findViewById(R.id.yearEditText);
        semesterEditText = findViewById(R.id.semesterEditText);
        sectionEditText = findViewById(R.id.sectionEditText);

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForm();
            }
        });

        // Find the Submit button
        Button submitButton = findViewById(R.id.submitButton);

        // Set OnClickListener for the Submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields
                if (validateInputs()) {
                    // If inputs are valid, proceed with registration logic
                    registerUser();
                }
            }
        });
    }

    // Method to validate input fields
    private boolean validateInputs() {
        // Check if any field is empty
        if (rollEditText.getText().toString().trim().isEmpty() ||
                nameEditText.getText().toString().trim().isEmpty() ||
                courseEditText.getText().toString().trim().isEmpty() ||
                branchEditText.getText().toString().trim().isEmpty() ||
                yearEditText.getText().toString().trim().isEmpty() ||
                semesterEditText.getText().toString().trim().isEmpty() ||
                sectionEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate Roll Number (must be a mix of characters and numbers)
        String rollNumber = rollEditText.getText().toString().trim();
        if (!rollNumber.matches("^[a-zA-Z0-9]+$")) {
            Toast.makeText(this, "Roll Number must contain a mix of characters and numbers", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate Year (must be an integer)
        String year = yearEditText.getText().toString().trim();
        if (!TextUtils.isDigitsOnly(year)) {
            Toast.makeText(this, "Year must be an integer", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate Semester (must be an integer)
        String semester = semesterEditText.getText().toString().trim();
        if (!TextUtils.isDigitsOnly(semester)) {
            Toast.makeText(this, "Semester must be an integer", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate Section (must be uppercase characters)
        String section = sectionEditText.getText().toString().trim();
        if (!section.matches("^[A-Z]+$")) {
            Toast.makeText(this, "Section must contain only uppercase characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        // All fields are valid
        return true;
    }

    // Method to reset form fields
    private void resetForm() {
        rollEditText.setText("");
        nameEditText.setText("");
        courseEditText.setText("");
        branchEditText.setText("");
        yearEditText.setText("");
        semesterEditText.setText("");
        sectionEditText.setText("");
    }

    // Method to perform registration logic
    private void registerUser() {
        // Fetch input values
        String rollNumber = rollEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String course = courseEditText.getText().toString().trim();
        String branch = branchEditText.getText().toString().trim();
        int year = Integer.parseInt(yearEditText.getText().toString().trim());
        int semester = Integer.parseInt(semesterEditText.getText().toString().trim());
        String section = sectionEditText.getText().toString().trim();

        // Check if the roll number already exists in the database
        databaseReference.child(rollNumber).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        // Roll number already exists, notify the user
                        Toast.makeText(RegistrationActivity.this, "Roll number already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        // Roll number doesn't exist, proceed with registration
                        // Create a Registration object
                        registration registration = new registration(rollNumber, name, course, branch, year, semester, section);

                        // Add the Registration object to the database using roll number as key
                        databaseReference.child(rollNumber).setValue(registration)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    // Error occurred while checking for roll number existence
                    Toast.makeText(RegistrationActivity.this, "Error checking roll number existence", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
