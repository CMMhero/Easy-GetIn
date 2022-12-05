package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class ItemActivity extends AppCompatActivity {

    ImageView imageTV;
    TextView nameTV, locationTV, jamPertamaTV, jamBerikutnyaTV;

    private String name;
    private String location;
    private int jamPertama;
    private int jamBerikutnya;

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
        jamPertama = getIntent().getExtras().getInt("jamPertama");
        jamBerikutnya = getIntent().getExtras().getInt("jamBerikutnya");

        nameTV.setText(name);
        locationTV.setText(location);
        jamPertamaTV.setText((int) jamPertama);
        jamBerikutnyaTV.setText((int) jamBerikutnya);
    }

    public void back(View view) {
        finish();
    }

    public void pesan(View view) {
        DocumentReference documentReference;
        documentReference = Functions.getCollectionReferenceForOrders().document();

        String nomorParkir = Functions.getRandomLetter() + Functions.getRandomNumber(1, 10);

        Order order = new Order(false, Timestamp.now(), name, nomorParkir);
        documentReference.set(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Functions.showToast(ItemActivity.this, "Berhasil pesan");

                    Intent parkirIntent = new Intent(ItemActivity.this, ParkiranActivity.class);
                    startActivity(parkirIntent);
                } else {
                    Functions.showToast(ItemActivity.this, "Gagal memesan. Mohon coba lagi");
                }
            }
        });
    }
}