package id.ac.umn.easygetin;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Functions {

    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static String getRandomLetter() {
        int randomNumber = (int) (Math.random() * 26);
        char randomChar = (char) ('a' + randomNumber);
        String randomAlphabet = String.valueOf(randomChar).toUpperCase();
        return randomAlphabet;
    }

    static int getRandomNumber(int min, int max) {
        int randomNumber = (int) Math.floor(Math.random() * (max - min + 1)) + min;
        return randomNumber;
    }

    static CollectionReference getCollectionReferenceForLocations() {
        return FirebaseFirestore.getInstance().collection("places")
                .document("all").collection("place");
    }

    static CollectionReference getCollectionReferenceForOrders(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("orders")
                .document(user.getUid()).collection("my_order");
    }

    static CollectionReference getCollectionReferenceForVehicle(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("vehicle")
                .document(user.getUid()).collection("my_vehicle");
    }

    static String convertTimeToString(Timestamp timestamp) {
        Date date = timestamp.toDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    static String generateCode(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
