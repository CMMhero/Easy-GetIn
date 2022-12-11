package id.ac.umn.easygetin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class EditActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
//    private ImageView kotakFoto;

    EditText tipeMobilET, warnaMobilET, platNomorMobilET;
    Button fotoBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tipeMobilET = findViewById(R.id.jenisMobilET);
        warnaMobilET = findViewById(R.id.warnaMobilET);
        platNomorMobilET = findViewById(R.id.platNomorET);
//        kotakFoto = findViewById(R.id.fotoMobilIV);
        fotoBtn = findViewById(R.id.fotoMobilBtn);

        fotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }


    public void saveData(View view) {
        DocumentReference documentReference;

        documentReference = Functions.getCollectionReferenceForVehicle().document();

        Vehicle vehicle = new Vehicle(platNomorMobilET.getText().toString(), warnaMobilET.getText().toString(), tipeMobilET.getText().toString());
        documentReference.set(vehicle).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Functions.showToast(EditActivity.this, "Berhasil update mobil");

                    Intent profileIntent = new Intent(EditActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                } else {
                    Functions.showToast(EditActivity.this, "Gagal update. Mohon coba lagi");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Uri mImageUri = data.getData();
            saveImageToFirebase(mImageUri);
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
                if (task.isSuccessful()) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;
//                            Functions.showToast(EditActivity.this, downloadUrl.toString());
                        }
                    });
                    Functions.showToast(EditActivity.this, "Successfully uploaded image");
                } else {
                    Functions.showToast(EditActivity.this, "Unable to upload image, please try again");
                }
            }
        });
    }
}