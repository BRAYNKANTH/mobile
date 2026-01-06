package com.example.sllifeline.activities.emergency;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sllifeline.R;
import com.example.sllifeline.database.DatabaseHelper;

public class CreateBloodRequestActivity extends AppCompatActivity {

    EditText etBlood, etRadius;
    Button btnSend;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_blood_request);

        etBlood = findViewById(R.id.etBloodGroup);
        etRadius = findViewById(R.id.etRadius);
        btnSend = findViewById(R.id.btnSendRequest);

        db = new DatabaseHelper(this);

        btnSend.setOnClickListener(v -> {

            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        100
                );
                return;
            }

            // TEMP values (replace with real GPS later)
            double latitude = 6.9271;
            double longitude = 79.8612;

            boolean inserted = db.insertBloodRequest(
                    1, // TEMP hospital_id
                    etBlood.getText().toString(),
                    Integer.parseInt(etRadius.getText().toString()),
                    latitude,
                    longitude
            );

            if (inserted) {
                Toast.makeText(this, "Emergency request sent", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Request failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}