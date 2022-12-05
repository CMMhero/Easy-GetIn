package id.ac.umn.easygetin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LocationAdapter extends FirestoreRecyclerAdapter<Location, LocationAdapter.LocationViewHolder>{
    Context context;

    public LocationAdapter(@NonNull FirestoreRecyclerOptions<Location> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull LocationViewHolder holder, int position, @NonNull Location model) {
        holder.nameTV.setText(model.name);
        holder.locationTV.setText(model.location);
//        holder.jamPertamaTV.setText((int) model.jamPertama);
//        holder.jamBerikutnyaTV.setText((int) model.jamBerikutnya);

        holder.itemView.setOnClickListener((view -> {
            Intent ItemItent = new Intent(context, ItemActivity.class);
            ItemItent.putExtra("name", model.name);
            ItemItent.putExtra("location", model.location);
            ItemItent.putExtra("jamPertama", model.jamPertama);
            ItemItent.putExtra("jamBerikutnya", model.jamBerikutnya);
            view.getContext().startActivity(ItemItent);
        }));
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        return new LocationViewHolder(view);
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, locationTV;
        ImageView imageIV;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.locationNameTextView);
            locationTV = itemView.findViewById(R.id.locationLocationTextView);
//            jamPertamaTV = itemView.findViewById(R.id.ItemJamPertama);
//            jamBerikutnyaTV = itemView.findViewById(R.id.ItemJamBerikutnya);
//            imageIV = itemView.findViewById(R.id.ItemGambar);
        }
    }
}
