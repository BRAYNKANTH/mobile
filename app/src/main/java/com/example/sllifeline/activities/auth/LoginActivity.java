package com.example.sllifeline.activities.auth;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sllifeline.R;
import com.example.sllifeline.activities.donor.DonorRegistrationActivity;
import com.example.sllifeline.database.DatabaseHelper;
import com.example.sllifeline.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        db = new DatabaseHelper(this);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor cursor = db.loginUser(email, password);

            if (cursor.moveToFirst()) {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));

                // Save session
                SessionManager session = new SessionManager(this);
                session.createSession(userId, role);

                // Redirect all users to DonorRegistrationActivity for now
                startActivity(new Intent(this, DonorRegistrationActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        });
    }
}
