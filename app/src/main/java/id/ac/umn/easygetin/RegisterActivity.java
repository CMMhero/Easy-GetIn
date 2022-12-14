package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameET, emailET, passwordET, confirmPasswordET;
    Button confirmButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = findViewById(R.id.RegisterUsernameEditText);
        emailET = findViewById(R.id.RegisterEmailEditText);
        passwordET = findViewById(R.id.RegisterPasswordEditText);
        confirmPasswordET = findViewById(R.id.RegisterConfirmPasswordEditText);
        ImageButton backButton = findViewById(R.id.RegisterBackButton);
        confirmButton = findViewById(R.id.RegisterConfirmButton);
        progressBar = findViewById(R.id.progressBar);

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
            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        registerAccount(username, email, password);
    }

    void registerAccount(String username, String email, String password) {
        showProgressBar(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();

                            firebaseAuth.getCurrentUser().updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            showProgressBar(false);
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Successfully created account", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void showProgressBar(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            confirmButton.setVisibility(View.VISIBLE);
        }
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