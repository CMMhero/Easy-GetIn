package id.ac.umn.easygetin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParkiranActivity extends AppCompatActivity {

    TextView jamMasukTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkiran);

        jamMasukTV = findViewById(R.id.JamMasuk);

        String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new java.util.Date());
        jamMasukTV.setText("Jam Masuk: " + timeStamp);
    }
}