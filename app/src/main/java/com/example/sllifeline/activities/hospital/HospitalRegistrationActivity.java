package com.example.sllifeline.activities.hospital; 
import android.content.Intent; 
import android.os.Bundle; 
import android.widget.*; 
import androidx.appcompat.app.AppCompatActivity; 
import com.example.sllifeline.R; 
import com.example.sllifeline.database.DatabaseHelper; 
public class HospitalRegistrationActivity extends AppCompatActivity { 
EditText etName, etRegNo, etPhone, etAddress, etCity, etDistrict; 
Button btnRegister; 
DatabaseHelper db; 
    @Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_hospital_registration); 
 
        etName = findViewById(R.id.etHospitalName); 
        etRegNo = findViewById(R.id.etRegNo); 
        etPhone = findViewById(R.id.etPhone); 
        etAddress = findViewById(R.id.etAddress); 
        etCity = findViewById(R.id.etCity); 
        etDistrict = findViewById(R.id.etDistrict); 
        btnRegister = findViewById(R.id.btnRegisterHospital); 
 
        db = new DatabaseHelper(this); 
 
        btnRegister.setOnClickListener(v -> { 
 
            if (etRegNo.getText().toString().trim().isEmpty()) { 
                Toast.makeText(this, "Registration number is required", 
Toast.LENGTH_SHORT).show(); 
                return; 
            } 
 
            boolean inserted = db.insertHospital( 
                    1, // TEMP user_id (SessionManager will replace) 
                    etName.getText().toString(), 
                    etRegNo.getText().toString(), 
                    etPhone.getText().toString(), 
                    etAddress.getText().toString(), 
                    etCity.getText().toString(), 
                    etDistrict.getText().toString() 
            ); 
 
            if (inserted) { 
                startActivity(new Intent(this, HospitalHomeActivity.class)); 
                finish(); 
            } else { 
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show(); 
            } 
        }); 
    } 
} 
