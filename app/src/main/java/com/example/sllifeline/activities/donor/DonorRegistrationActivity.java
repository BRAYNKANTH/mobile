package com.example.sllifeline.activities.donor;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sllifeline.R;
import com.example.sllifeline.database.DatabaseHelper;
public class DonorRegistrationActivity extends AppCompatActivity {
    EditText etName, etNIC, etAge, etBlood, etPhone, etAddress, etCity, etDistrict;
    Button btnRegister;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_registration);

        etName = findViewById(R.id.etName);
        etNIC = findViewById(R.id.etNIC);
        etAge = findViewById(R.id.etAge);
        etBlood = findViewById(R.id.etBloodGroup);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etDistrict = findViewById(R.id.etDistrict);
        btnRegister = findViewById(R.id.btnRegisterDonor);

        db = new DatabaseHelper(this);

        btnRegister.setOnClickListener(v -> {

            if (etNIC.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "NIC is required", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.insertDonor(
                    1, // TEMP user_id (SessionManager will replace)
                    etName.getText().toString(),
                    etNIC.getText().toString(),
                    Integer.parseInt(etAge.getText().toString()),
                    etBlood.getText().toString(),
                    etPhone.getText().toString(),
                    etAddress.getText().toString(),
                    etCity.getText().toString(),
                    etDistrict.getText().toString()
            );

            if (inserted) {
                startActivity(new Intent(this, DonorHomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
