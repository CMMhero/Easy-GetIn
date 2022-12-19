package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class EditActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView kotakFoto;
    EditText tipeMobilET, warnaMobilET, platNomorMobilET;

    String tipeMobil, warnaMobil, platNomorMobil;
    Button fotoBtn;
    Button saveBtn;

    boolean samePhoto;
    ProgressBar progressBar;

    Uri globalImageUri;
    Uri downloadUrl;

    DocumentReference docRef = Functions.getDocumentReferenceForVehicle();
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tipeMobilET = findViewById(R.id.tipeMobilET);
        warnaMobilET = findViewById(R.id.warnaMobilET);
        platNomorMobilET = findViewById(R.id.platNomorET);
        kotakFoto = findViewById(R.id.fotoMobilIV);
        fotoBtn = findViewById(R.id.fotoMobilBtn);
        saveBtn = findViewById(R.id.saveMobilBtn);
        progressBar = findViewById(R.id.progressBarEditMobil);

        fotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        docId = document.getId();
                        tipeMobilET.setText(document.get("jenis").toString());
                        warnaMobilET.setText(document.get("warna").toString());
                        platNomorMobilET.setText(document.get("platNomor").toString());

                        if (document.get("photo") != null) {
                            samePhoto = true;
                            globalImageUri = Uri.parse(document.get("photo").toString());
                            downloadUrl = Uri.parse(document.get("photo").toString());
                            Picasso.get().load(globalImageUri).into(kotakFoto);
//                            kotakFoto.setImageURI(Uri.parse(document.get("photo").toString()));
                        }
                    }
                } else {
                    Functions.showToast(EditActivity.this, "Error while fetching vehicle data");
                }
            }
        });

    }


    public void saveData(View view) {
        showProgressBar(true);

        tipeMobil = tipeMobilET.getText().toString();
        warnaMobil = warnaMobilET.getText().toString();
        platNomorMobil = platNomorMobilET.getText().toString();

        if (tipeMobil.isEmpty() || warnaMobil.isEmpty() || platNomorMobil.isEmpty() || globalImageUri == null) {
            Functions.showToast(EditActivity.this, "Semua data harus diisi");
            showProgressBar(false);
            return;
        }

        if (!samePhoto) {
            saveImageToFirebase(globalImageUri);
        } else {
            Vehicle vehicle = new Vehicle(platNomorMobil, warnaMobil, tipeMobil);
            vehicle.setPhoto(globalImageUri.toString());

            docRef.set(vehicle).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        showProgressBar(false);
                        Functions.showToast(EditActivity.this, "Data saved");

                        Intent profileIntent = new Intent(EditActivity.this, ProfileActivity.class);
                        startActivity(profileIntent);
                    } else {
                        showProgressBar(false);
                        Functions.showToast(EditActivity.this, "An error occured, please try again");
                    }
                }
            });
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            globalImageUri = data.getData();
            kotakFoto.setImageURI(globalImageUri);
            samePhoto = false;
        }
    }

    void showProgressBar(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            saveBtn.setVisibility(View.VISIBLE);
        }
    }

    private void saveImageToFirebase(Uri imageUri) {

        // Get a reference to the default storage bucket
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Create a reference to the image file in the storage bucket
        String fileName = UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = storageRef.child(fileName);

//        UploadTask uploadTask = imageRef.putFile(imageUri);

        imageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                showProgressBar(true);
                if (task.isSuccessful()) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            downloadUrl = uri;

                            Vehicle vehicle = new Vehicle(platNomorMobil, warnaMobil, tipeMobil);
                            vehicle.setPhoto(downloadUrl.toString());

                            docRef.set(vehicle).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        showProgressBar(false);
                                        Functions.showToast(EditActivity.this, "Data saved");

                                        Intent profileIntent = new Intent(EditActivity.this, ProfileActivity.class);
                                        startActivity(profileIntent);
                                    } else {
                                        showProgressBar(false);
                                        Functions.showToast(EditActivity.this, "An error occured, please try again");
                                    }
                                }
                            });
//                            Functions.showToast(EditActivity.this, downloadUrl.toString());
                        }
                    });
//                    Functions.showToast(EditActivity.this, "Successfully uploaded image");
                } else {
                    showProgressBar(false);
                    Functions.showToast(EditActivity.this, "Unable to upload image, please try again");
                }
            }
        });
    }
}