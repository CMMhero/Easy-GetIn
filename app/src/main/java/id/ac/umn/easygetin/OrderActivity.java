package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.Query;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;

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
        navigation.setSelectedItemId(R.id.nav_order);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        nameTV = findViewById(R.id.Name);
//        jamMasukTV = findViewById(R.id.JamMasuk);
//        nomorParkirTV = findViewById(R.id.NoParkir);
//        codeTV = findViewById(R.id.Code);
//
//        String timestampFormat = getIntent().getExtras().getString("timestampFormat");
//        String name = getIntent().getExtras().getString("name");
//        String nomorParkir = getIntent().getExtras().getString("nomorParkir");
//        int code = getIntent().getExtras().getInt("code");
//
//        nameTV.setText(name);
//        nomorParkirTV.setText("Nomor Parkir: " + nomorParkir);
//        jamMasukTV.setText("Jam Masuk: " + timestampFormat);
//        codeTV.setText("CODE:" + code);

        Query query = Functions.getCollectionReferenceForOrders().whereEqualTo("finished", false).orderBy("start", Query.Direction.DESCENDING);;
        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>().setQuery(query, Order.class).build();
        mRecyclerView = findViewById(R.id.OrderRecyclerView);
        mAdapter = new OrderAdapter(options, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}