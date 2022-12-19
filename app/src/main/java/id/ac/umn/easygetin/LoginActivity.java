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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailET, passwordET;
    ProgressBar progressBar;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.LoginEmailEditText);
        passwordET = findViewById(R.id.LoginPasswordEditText);
        ImageButton backButton = findViewById(R.id.LoginBackButton);
        confirmButton = findViewById(R.id.LoginConfirmButton);
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
                login();
//                startActivity(new Intent(activity_login.this, activity_home.class));
//                finish();
            }
        });
    }

    void login() {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        String error = validateLogin(email, password);
        if (error.length() > 0) {
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        loginAccount(email, password);
    }

    void loginAccount(String email, String password) {
        showProgressBar(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    showProgressBar(false);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    showProgressBar(false);
                }
            }
        });
    }

    void showProgressBar(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.GONE);
        }
    }

    String validateLogin(String email, String password) {

        if (email.isEmpty() || password.isEmpty()) {
            return "All fields must be filled";
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Email is invalid";
        }

        if (password.length() < 6) {
            return "Password must be at least 6 characters";
        }

        return "";
    }
}