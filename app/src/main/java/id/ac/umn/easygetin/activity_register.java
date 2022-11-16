package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_register extends AppCompatActivity {

    EditText usernameET, emailET, passwordET, confirmPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = findViewById(R.id.RegisterUsernameEditText);
        emailET = findViewById(R.id.RegisterEmailEditText);
        passwordET = findViewById(R.id.RegisterPasswordEditText);
        confirmPasswordET = findViewById(R.id.RegisterConfirmPasswordEditText);
        ImageButton backButton = findViewById(R.id.RegisterBackButton);
        Button confirmButton = findViewById(R.id.RegisterConfirmButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
//                startActivity(new Intent(activity_register.this, activity_home.class));
//                finish();
            }
        });
    }

    void createAccount() {
        String username = usernameET.getText().toString();
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String confirmPassword = confirmPasswordET.getText().toString();

        String error = validateRegister(username, email, password, confirmPassword);
        if (error.length() > 0) {
            Toast.makeText(activity_register.this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        registerAccount(username, email, password);
    }

    void registerAccount(String username, String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity_register.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity_register.this, "Successfully created account", Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                        } else {
                            Toast.makeText(activity_register.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    String validateRegister(String username, String email, String password, String confirmPassword) {

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return "All fields must be filled";
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Email is invalid";
        }

        if (password.length() < 6) {
            return "Password must be at least 6 characters";
        }

        if (!password.equals(confirmPassword)) {
            return "Password does not match";
        }

        return "";
    }
}