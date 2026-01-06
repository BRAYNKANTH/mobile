package com.example.sllifeline.activities.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sllifeline.R;
import com.example.sllifeline.utils.SessionManager; // Make sure SessionManager is imported

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        // ======= AUTO-LOGIN LOGIC START =======
        SessionManager session = new SessionManager(this);

        if (session.isLoggedIn()) {
            if ("DONOR".equals(session.getRole())) {
                startActivity(new Intent(this,
                        com.example.sllifeline.activities.donor.DonorHomeActivity.class));
            } else {
                startActivity(new Intent(this,
                        com.example.sllifeline.activities.hospital.HospitalHomeActivity.class));
            }
            finish(); // Close WelcomeActivity
            return; // Stop executing rest of onCreate
        }
        // ======= AUTO-LOGIN LOGIC END =======

        // Edge-to-edge padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
