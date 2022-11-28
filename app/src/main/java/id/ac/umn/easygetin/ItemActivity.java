package id.ac.umn.easygetin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    ImageView imageTV;
    TextView nameTV, locationTV, jamPertamaTV, jamBerikutnyaTV;

    private String name;
    private String location;

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

        nameTV.setText(name);
        locationTV.setText(location);
    }

    public void back(View view) {
        finish();
    }

    public void pesan(View view) {
        Intent parkirIntent = new Intent(ItemActivity.this, ParkiranActivity.class);
        startActivity(parkirIntent);
    }
}