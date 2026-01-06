package com.example.sllifeline.activities.auth;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sllifeline.R;
import com.example.sllifeline.database.DatabaseHelper;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText etEmail, etIdentity, etNewPass, etConfirmPass;
    RadioGroup rgRole;
    Button btnReset;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmail = findViewById(R.id.etEmail);
        etIdentity = findViewById(R.id.etIdentity);
        etNewPass = findViewById(R.id.etNewPassword);
        etConfirmPass = findViewById(R.id.etConfirmPassword);
        rgRole = findViewById(R.id.rgRole);
        btnReset = findViewById(R.id.btnResetPassword);

        db = new DatabaseHelper(this);

        btnReset.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String identity = etIdentity.getText().toString().trim();
            String newPass = etNewPass.getText().toString().trim();
            String confirmPass = etConfirmPass.getText().toString().trim();

            if (email.isEmpty() || identity.isEmpty() ||
                    newPass.isEmpty() || confirmPass.isEmpty() ||
                    rgRole.getCheckedRadioButtonId() == -1) {

                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔒 Identity verification logic (simplified)
            boolean verified = db.verifyIdentity(email, identity);

            if (!verified) {
                Toast.makeText(this, "Identity verification failed", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean updated = db.updatePassword(email, newPass);

            if (updated) {
                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Password update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
