package com.example.sllifeline.activities.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sllifeline.R;
import com.example.sllifeline.utils.SessionManager;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        // ======= AUTO-LOGIN LOGIC =======
        SessionManager session = new SessionManager(this);
        
        if (session.isLoggedIn()) {
            if ("DONOR".equals(session.getRole())) {
                startActivity(new Intent(this,
                        com.example.sllifeline.activities.donor.DonorHomeActivity.class));
            } else {
                // If HospitalHomeActivity is missing, we redirect to DonorHome for now
                try {
                    startActivity(new Intent(this,
                            com.example.sllifeline.activities.donor.DonorHomeActivity.class));
                } catch (Exception e) {
                    // Fallback if DonorHome also has issues
                }
            }
            finish();
            return;
        }

        // ======= NAVIGATION TO LOGIN/SIGNUP =======
        // Note: You should add buttons to activity_welcome.xml for Login/Signup navigation
        // For now, if not logged in, the user stays on the Welcome Screen.

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
