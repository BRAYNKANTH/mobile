package com.example.sllifeline.activities.emergency;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sllifeline.R;

public class RequestHistoryActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_history);
        
        // Set action bar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Request History");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
