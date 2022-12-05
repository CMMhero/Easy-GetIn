package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent homeIntent = new Intent(OrderActivity.this, activity_home.class);
                    startActivity(homeIntent);
                    finish();
                    return true;
                case R.id.nav_profile:
                    Intent profileIntent = new Intent(OrderActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    finish();
                    return true;
                case R.id.nav_order:
                    Intent orderIntent = new Intent(OrderActivity.this, OrderActivity.class);
                    startActivity(orderIntent);
                    finish();
                    return true;
                case R.id.nav_history:
                    Intent historyIntent = new Intent(OrderActivity.this, HistoryActivity.class);
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
        setContentView(R.layout.activity_order);

        BottomNavigationView navigation = findViewById(R.id.navBar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}