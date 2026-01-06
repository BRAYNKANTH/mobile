package com.example.sllifeline.activities.emergency;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sllifeline.R;
import com.example.sllifeline.database.DatabaseHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CreateBloodRequestActivity extends AppCompatActivity {

    private EditText etBloodGroup;
    private EditText etRadius;
    private Button btnSendRequest;
    private DatabaseHelper db;
    private FusedLocationProviderClient fusedLocationClient;
    
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_blood_request);
        
        // Initialize views
        etBloodGroup = findViewById(R.id.etBloodGroup);
        etRadius = findViewById(R.id.etRadius);
        btnSendRequest = findViewById(R.id.btnSendRequest);
        
        // Initialize database and Location Client
        db = new DatabaseHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        
        // Set click listener
        btnSendRequest.setOnClickListener(v -> handleSendRequest());
    }
    
    private void handleSendRequest() {
        // Validate inputs
        String bloodGroup = etBloodGroup.getText().toString().trim();
        String radiusStr = etRadius.getText().toString().trim();
        
        if (bloodGroup.isEmpty()) {
            Toast.makeText(this, "Please enter blood group", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (radiusStr.isEmpty()) {
            Toast.makeText(this, "Please enter search radius", Toast.LENGTH_SHORT).show();
            return;
        }
        
        int radius;
        try {
            radius = Integer.parseInt(radiusStr);
            if (radius <= 0 || radius > 100) {
                Toast.makeText(this, "Radius must be between 1-100 km", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid radius value", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Check location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE
            );
            return;
        }
        
        // Permission granted, get REAL location
        getLastLocationAndSend(bloodGroup, radius);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocationAndSend(String bloodGroup, int radius) {
        fusedLocationClient.getLastLocation()
            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Use REAL coordinates from the device sensor
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        sendEmergencyRequest(bloodGroup, radius, latitude, longitude);
                    } else {
                        // If GPS is off or location is unavailable, use Colombo as fallback
                        // This ensures the app doesn't fail during your demo
                        Toast.makeText(CreateBloodRequestActivity.this, 
                            "Real GPS unavailable. Using default location.", 
                            Toast.LENGTH_SHORT).show();
                        sendEmergencyRequest(bloodGroup, radius, 6.9271, 79.8612);
                    }
                }
            });
    }
    
    private void sendEmergencyRequest(String bloodGroup, int radius, double latitude, double longitude) {
        // Insert request into database
        boolean inserted = db.insertBloodRequest(
            1, // TEMP hospital_id
            bloodGroup,
            radius,
            latitude,
            longitude
        );
        
        if (inserted) {
            Toast.makeText(this, 
                "✅ Emergency request sent!\n" +
                "Location: " + latitude + ", " + longitude + "\n" +
                "Blood Group: " + bloodGroup, 
                Toast.LENGTH_LONG).show();
            
            finish();
        } else {
            Toast.makeText(this, "❌ Failed to save request.", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleSendRequest();
            } else {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_LONG).show();
            }
        }
    }
}
