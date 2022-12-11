package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class ItemActivity extends AppCompatActivity {

    ImageView imageTV;
    TextView nameTV, locationTV, jamPertamaTV, jamBerikutnyaTV;

    private String name;
    private String location;
    private long jamPertama;
    private long jamBerikutnya;
    private String photoUrl;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    Intent homeIntent = new Intent(ItemActivity.this, HomeActivity.class);
//                    startActivity(homeIntent);
//                    finish();
//                    return true;
//                case R.id.nav_profile:
//                    Intent profileIntent = new Intent(ItemActivity.this, ProfileActivity.class);
//                    startActivity(profileIntent);
//                    finish();
//                    return true;
//                case R.id.nav_order:
//                    Intent orderIntent = new Intent(ItemActivity.this, OrderActivity.class);
//                    startActivity(orderIntent);
//                    finish();
//                    return true;
//                case R.id.nav_history:
//                    Intent historyIntent = new Intent(ItemActivity.this, HistoryActivity.class);
//                    startActivity(historyIntent);
//                    finish();
//                    return true;
//            }
//            return false;
//        }
//    };



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

        Query collection = FirebaseFirestore.getInstance().collection("places")
                .document("all").collection("place").whereEqualTo("name", name);

        collection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        location = (String) snapshot.get("location");
                        locationTV.setText(location);
                        jamPertama = (Long) snapshot.get("jamPertama");
                        jamPertamaTV.setText("Jam Pertama: " + Functions.convertToCurrencyString(jamPertama));
                        jamBerikutnya = (Long) snapshot.get("jamBerikutnya");
                        jamBerikutnyaTV.setText("Jam Berikutnya: " +  Functions.convertToCurrencyString(jamBerikutnya));
                        photoUrl = (String) snapshot.get("photoUrl");
                        Picasso.get().load(photoUrl).into(imageTV);
                    }
                } else {
                    // no locations
                }
            }
        });

        nameTV.setText(name);
//        locationTV.setText(location);
//        jamPertamaTV.setText("Jam Pertama: " + Functions.convertToCurrencyString(jamPertama));
//        jamBerikutnyaTV.setText("Jam Berikutnya: " +  Functions.convertToCurrencyString(jamBerikutnya));
//        Picasso.get()
//                .load(photoUrl)
//                .into(imageTV);
//        BottomNavigationView navigation = findViewById(R.id.navBar);
//        navigation.setSelectedItemId(R.id.nav_home);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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