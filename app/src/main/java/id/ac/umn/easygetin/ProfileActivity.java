package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    TextView usernameTV, emailTV;
//    Button save;
    FirebaseUser user;
    ImageView mobilIV;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent homeIntent = new Intent(ProfileActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                    return true;
                case R.id.nav_profile:
                    Intent profileIntent = new Intent(ProfileActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    finish();
                    return true;
                case R.id.nav_order:
                    Intent orderIntent = new Intent(ProfileActivity.this, OrderActivity.class);
                    startActivity(orderIntent);
                    finish();
                    return true;
                case R.id.nav_history:
                    Intent historyIntent = new Intent(ProfileActivity.this, HistoryActivity.class);
                    startActivity(historyIntent);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        usernameTV = findViewById(R.id.usernameTV);
        emailTV = findViewById(R.id.emailTV);
//        mobilIV = findViewById(R.id.mobilImageView);
//        save = findViewById(R.id.save);

        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            usernameTV.setText(name);
            emailTV.setText(email);
        }


        BottomNavigationView navigation = findViewById(R.id.navBar);
        navigation.setSelectedItemId(R.id.nav_profile);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

//    DocumentReference documentReference = fStore.collection("users").document(userId);
//
//    public void uploadImage(Uri imageUri) {
//        StorageReference fileRef = storageRef.child("profile.jpg");
//        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(ProfileActivity.this, "Updated Profile Picture", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(ProfileActivity.this, "Fail to update Profile Picture", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void logout(View view) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Functions.showToast(ProfileActivity.this, "Successfully logged out");
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }

    public void editData(View view) {
        Intent editIntent = new Intent(ProfileActivity.this, EditActivity.class);
        startActivity(editIntent);
    }
}