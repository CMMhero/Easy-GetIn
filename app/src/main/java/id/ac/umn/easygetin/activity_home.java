package id.ac.umn.easygetin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class activity_home extends AppCompatActivity {

    private ArrayList<Location> mDaftarLocation = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private LocationAdapter mAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            TextView toolbarTitle = findViewById(R.id.toolbarTitle);
            toolbarTitle.setText("Welcome, " + name);
        }

        toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);



        mDaftarLocation.add(new Location("Universitas Multimedia Nusantara", "Jl. Scientia Boulevard"));
        mDaftarLocation.add(new Location("Summarecon Mall Serpong", "Jl. Boulevard Raya"));
        mDaftarLocation.add(new Location("asdf", "Jl. Boulevard Raya"));
        mDaftarLocation.add(new Location("fda", "Jl. Boulevard Raya"));
        mDaftarLocation.add(new Location("were", "Jl. Boulevard Raya"));
        mDaftarLocation.add(new Location("aewrwe", "Jl. Boulevard Raya"));

        mRecyclerView = (RecyclerView) findViewById(R.id.LocationsRecyclerView);
        mAdapter = new LocationAdapter(this, mDaftarLocation);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void logout(View view) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Toast.makeText(activity_home.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(activity_home.this, activity_main.class));
        finish();
    }
}