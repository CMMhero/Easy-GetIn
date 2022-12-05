package id.ac.umn.easygetin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ParkiranActivity extends AppCompatActivity {

    TextView nameTV, nomorParkirTV, jamMasukTV, codeTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkiran);

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