package com.example.sllifeline.activities.emergency;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sllifeline.R;

public class NearbyDonorsActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_donors);
        
        // Set action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Nearby Donors");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
