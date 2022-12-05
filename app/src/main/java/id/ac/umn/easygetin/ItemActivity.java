package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class ItemActivity extends AppCompatActivity {

    ImageView imageTV;
    TextView nameTV, locationTV, jamPertamaTV, jamBerikutnyaTV;

    private String name;
    private String location;
    private double jamPertama;
    private double jamBerikutnya;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent homeIntent = new Intent(ItemActivity.this, activity_home.class);
                    startActivity(homeIntent);
                    finish();
                    return true;
                case R.id.nav_profile:
                    Intent profileIntent = new Intent(ItemActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    finish();
                    return true;
                case R.id.nav_order:
                    Intent orderIntent = new Intent(ItemActivity.this, OrderActivity.class);
                    startActivity(orderIntent);
                    finish();
                    return true;
                case R.id.nav_history:
                    Intent historyIntent = new Intent(ItemActivity.this, HistoryActivity.class);
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
        setContentView(R.layout.activity_item);

        imageTV = findViewById(R.id.ItemGambar);
        nameTV = findViewById(R.id.ItemName);
        locationTV = findViewById(R.id.ItemLocation);
        jamPertamaTV = findViewById(R.id.ItemJamPertama);
        jamBerikutnyaTV = findViewById(R.id.ItemJamBerikutnya);

        name = getIntent().getExtras().getString("name");
        location = getIntent().getExtras().getString("location");
        jamPertama = getIntent().getExtras().getDouble("jamPertama");
        jamBerikutnya = getIntent().getExtras().getDouble("jamBerikutnya");

        nameTV.setText(name);
        locationTV.setText(location);
        jamPertamaTV.setText("Jam Pertama: " + Double.toString(jamPertama));
        jamBerikutnyaTV.setText("Jam Berikutnya: " + Double.toString(jamBerikutnya));

        BottomNavigationView navigation = findViewById(R.id.navBar);
        navigation.setSelectedItemId(R.id.nav_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void back(View view) {
        finish();
    }

    public void pesan(View view) {
        DocumentReference documentReference;
        documentReference = Functions.getCollectionReferenceForOrders().document();

        String nomorParkir = Functions.getRandomLetter() + Functions.getRandomNumber(1, 10);

        Timestamp timestamp = Timestamp.now();
//        String timestampFormat = Functions.convertTimeToString(timestamp);
        String code = Functions.generateCode(12);

        Order order = new Order(false, timestamp, name, nomorParkir, code);
        documentReference.set(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Functions.showToast(ItemActivity.this, "Berhasil pesan");

                    Intent orderIntent = new Intent(ItemActivity.this, OrderActivity.class);
                    startActivity(orderIntent);
                } else {
                    Functions.showToast(ItemActivity.this, "Gagal memesan. Mohon coba lagi");
                }
            }
        });
    }
}