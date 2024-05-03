package com.example.studentapp;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayDataActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        // Initialize FirebaseDatabase
        String firebaseUrl = "https://studentapp-abfd2-default-rtdb.firebaseio.com/";
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(firebaseUrl).child("users");

        // Initialize TableLayout
        tableLayout = findViewById(R.id.tableLayout);

        // Add header row
        addHeaderRow();

        // Fetch data from Firebase
        fetchDataFromFirebase();
    }

    private void addHeaderRow() {
        TableRow headerRow = new TableRow(this);
        headerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView rollHeader = new TextView(this);
        rollHeader.setText("Roll Number");
        rollHeader.setPadding(8, 8, 8, 8);
        headerRow.addView(rollHeader);

        TextView nameHeader = new TextView(this);
        nameHeader.setText("Name");
        nameHeader.setPadding(8, 8, 8, 8);
        headerRow.addView(nameHeader);

        TextView courseHeader = new TextView(this);
        courseHeader.setText("Course");
        courseHeader.setPadding(8, 8, 8, 8);
        headerRow.addView(courseHeader);

        TextView branchHeader = new TextView(this);
        branchHeader.setText("Branch");
        branchHeader.setPadding(8, 8, 8, 8);
        headerRow.addView(branchHeader);

        TextView yearHeader = new TextView(this);
        yearHeader.setText("Year");
        yearHeader.setPadding(8, 8, 8, 8);
        headerRow.addView(yearHeader);

        TextView semesterHeader = new TextView(this);
        semesterHeader.setText("Semester");
        semesterHeader.setPadding(8, 8, 8, 8);
        headerRow.addView(semesterHeader);

        TextView sectionHeader = new TextView(this);
        sectionHeader.setText("Section");
        sectionHeader.setPadding(8, 8, 8, 8);
        headerRow.addView(sectionHeader);

        tableLayout.addView(headerRow);
    }

    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear existing data before updating
                tableLayout.removeViews(1, tableLayout.getChildCount() - 1);

                // Iterate through each child node (each user)
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get user data
                    String roll = snapshot.child("roll").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String course = snapshot.child("course").getValue(String.class);
                    String branch = snapshot.child("branch").getValue(String.class);
                    int year = snapshot.child("year").getValue(Integer.class);
                    int semester = snapshot.child("semester").getValue(Integer.class);
                    String section = snapshot.child("section").getValue(String.class);

                    // Create a new row in the table layout
                    TableRow row = new TableRow(DisplayDataActivity.this);
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView rollTextView = new TextView(DisplayDataActivity.this);
                    rollTextView.setText(roll);
                    rollTextView.setPadding(8, 8, 8, 8);
                    row.addView(rollTextView);

                    TextView nameTextView = new TextView(DisplayDataActivity.this);
                    nameTextView.setText(name);
                    nameTextView.setPadding(8, 8, 8, 8);
                    row.addView(nameTextView);

                    TextView courseTextView = new TextView(DisplayDataActivity.this);
                    courseTextView.setText(course);
                    courseTextView.setPadding(8, 8, 8, 8);
                    row.addView(courseTextView);

                    TextView branchTextView = new TextView(DisplayDataActivity.this);
                    branchTextView.setText(branch);
                    branchTextView.setPadding(8, 8, 8, 8);
                    row.addView(branchTextView);

                    TextView yearTextView = new TextView(DisplayDataActivity.this);
                    yearTextView.setText(String.valueOf(year));
                    yearTextView.setPadding(8, 8, 8, 8);
                    row.addView(yearTextView);

                    TextView semesterTextView = new TextView(DisplayDataActivity.this);
                    semesterTextView.setText(String.valueOf(semester));
                    semesterTextView.setPadding(8, 8, 8, 8);
                    row.addView(semesterTextView);

                    TextView sectionTextView = new TextView(DisplayDataActivity.this);
                    sectionTextView.setText(section);
                    sectionTextView.setPadding(8, 8, 8, 8);
                    row.addView(sectionTextView);

                    // Add the row to the table layout
                    tableLayout.addView(row);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
