package com.example.sllifeline.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sllifeline.R;
import com.example.sllifeline.activities.donor.DonorRegistrationActivity;
import com.example.sllifeline.activities.hospital.HospitalRegistrationActivity;
import com.example.sllifeline.database.DatabaseHelper;

public class SignupActivity extends AppCompatActivity {

    EditText etEmail, etPassword, etConfirm;
    RadioGroup rgRole;
    Button btnSignup;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirmPassword);
        rgRole = findViewById(R.id.rgRole);
        btnSignup = findViewById(R.id.btnCreateAccount);

        db = new DatabaseHelper(this);

        btnSignup.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();
            String confirm = etConfirm.getText().toString().trim();

            int selected = rgRole.getCheckedRadioButtonId();

            if (email.isEmpty() || pass.isEmpty() || confirm.isEmpty() || selected == -1) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            String role = (selected == R.id.rbDonor) ? "DONOR" : "HOSPITAL";

            boolean inserted = db.insertUser(email, pass, role);

            if (inserted) {
                if (role.equals("DONOR")) {
                    startActivity(new Intent(this, DonorRegistrationActivity.class));
                } else {
                    startActivity(new Intent(this, HospitalRegistrationActivity.class));
                }
                finish();
            } else {
                Toast.makeText(this, "Signup failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
