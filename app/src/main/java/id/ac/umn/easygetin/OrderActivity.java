package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

    TextView nameTV, nomorParkirTV, jamMasukTV, codeTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        BottomNavigationView navigation = findViewById(R.id.navBar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        nameTV = findViewById(R.id.Name);
        jamMasukTV = findViewById(R.id.JamMasuk);
        nomorParkirTV = findViewById(R.id.NoParkir);
        codeTV = findViewById(R.id.Code);

        String timestampFormat = getIntent().getExtras().getString("timestampFormat");
        String name = getIntent().getExtras().getString("name");
        String nomorParkir = getIntent().getExtras().getString("nomorParkir");
        int code = getIntent().getExtras().getInt("code");

        nameTV.setText(name);
        nomorParkirTV.setText("Nomor Parkir: " + nomorParkir);
        jamMasukTV.setText("Jam Masuk: " + timestampFormat);
        codeTV.setText("CODE:" + code);
    }

    public void selesai(View view) {
    }
}