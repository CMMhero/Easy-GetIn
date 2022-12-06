package id.ac.umn.easygetin;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OrderAdapter extends FirestoreRecyclerAdapter<Order, OrderAdapter.OrderViewHolder>{
    Context context;

    public OrderAdapter(@NonNull FirestoreRecyclerOptions<Order> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {
        holder.nameTV.setText(model.locationName);
        holder.nomorParkirTV.setText("Nomor Parkir: " + model.nomorParkir);
        holder.jamMasukTV.setText("Jam Masuk: " + Functions.convertTimeToString(model.start));
        String imageUrl = "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=" + model.code;
        String docId = this.getSnapshots().getSnapshot(position).getId();

        Timestamp timestampNow = Timestamp.now();

        if (Functions.covertTimeToTimestamp(timestampNow) - Functions.covertTimeToTimestamp(model.start) > 60000) {
            holder.cancelButton.setVisibility(View.GONE);
        }

        holder.selesaiButton.setOnClickListener((view -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(view.getContext());
            bottomSheetDialog.setContentView(R.layout.modal_qr);

            TextView nameModal = bottomSheetDialog.findViewById(R.id.nameModal);
            TextView codeModal = bottomSheetDialog.findViewById(R.id.codeModal);

            ImageView qrModal = bottomSheetDialog.findViewById(R.id.qrModal);

            nameModal.setText(model.locationName);
            codeModal.setText(model.code);
            Picasso.get()
                    .load(imageUrl)
                    .into(qrModal);

            bottomSheetDialog.show();

//            EDIT KE DATABASE -> ORDER SELESAI
//            Timestamp timestampEnd = Timestamp.now();
//            Order order = new Order(true, model.start, model.locationName, model.nomorParkir, model.code);
//            order.setEnd(timestampEnd);
//
//            DocumentReference documentReference = Functions.getCollectionReferenceForOrders().document(docId);
//
//            documentReference.set(order).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()) {
//                        Functions.showToast(view.getContext(), "Parking finished");
//                    } else {
//                        Functions.showToast(view.getContext(), "An error occurred, please try again");
//                    }
//                }
//            });
        }));

        holder.cancelButton.setOnClickListener((view -> {

            Timestamp timestampClicked = Timestamp.now();
            if (Functions.covertTimeToTimestamp(timestampClicked) - Functions.covertTimeToTimestamp(model.start) > 60000) {
                holder.cancelButton.setVisibility(View.GONE);
                Functions.showToast(view.getContext(), "Anda tidak bisa mengcancel parkiran jika sudah lebih dari 1 menit");
                return;
            }

            DocumentReference documentReference = Functions.getCollectionReferenceForOrders().document(docId);

            documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Functions.showToast(view.getContext(), "Cancelled parking");
                    } else {
                        Functions.showToast(view.getContext(), "An error occurred, please try again");
                    }
                }
            });
        }));
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, jamMasukTV, nomorParkirTV;
//                codeTV;
        Button selesaiButton, cancelButton;
//        ImageView codeIV;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.NameOrder);
            jamMasukTV = itemView.findViewById(R.id.JamMasukOrder);
            nomorParkirTV = itemView.findViewById(R.id.NoParkirOrder);
//            codeTV = itemView.findViewById(R.id.CodeOrder);
//            codeIV = itemView.findViewById(R.id.CodeImage);

            selesaiButton = itemView.findViewById(R.id.SelesaiOrder);
            cancelButton = itemView.findViewById(R.id.CancelOrder);
        }
    }
}

