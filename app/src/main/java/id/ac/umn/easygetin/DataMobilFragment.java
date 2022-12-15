package id.ac.umn.easygetin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class DataMobilFragment extends Fragment {

    TextView tipeMobilTV, platNomorMobilTV;
    ImageView dataMobilIV;
    Button editDataMobilBtn;

    public DataMobilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_data_mobil, container, false);
        tipeMobilTV = v.findViewById(R.id.tipeMobilTV);
        platNomorMobilTV = v.findViewById(R.id.platNomorMobilTV);
        dataMobilIV = v.findViewById(R.id.dataMobilIV);
        editDataMobilBtn = v.findViewById(R.id.editDataMobilBtn);

        editDataMobilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(view.getContext(), EditActivity.class);
                startActivity(editIntent);
            }
        });

        DocumentReference docRef = Functions.getDocumentReferenceForVehicle();

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        tipeMobilTV.setText(document.get("jenis").toString());
                        platNomorMobilTV.setText(document.get("platNomor").toString());

                        if (document.get("photo") != null) {
                            Picasso.get().load(Uri.parse(document.get("photo").toString())).into(dataMobilIV);
                        }
                    }
                } else {
                }
            }
        });
        return v;
    }
}