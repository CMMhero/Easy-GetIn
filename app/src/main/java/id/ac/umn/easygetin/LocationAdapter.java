package id.ac.umn.easygetin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private final ArrayList<Location> mDaftarLocation;
    private LayoutInflater mInflater;

    LocationAdapter(Context context, ArrayList<Location> daftarLocation) {
        mInflater = LayoutInflater.from(context);
        mDaftarLocation = daftarLocation;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.location_item, parent, false);
        return new LocationViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder holder, int position) {
        final Location mCurrent = mDaftarLocation.get(position);
        holder.LocationNameItemView.setText(mCurrent.getName());
        holder.LocationItemView.setText(mCurrent.getLocation());
//        holder.LocationThumbnailView.setImageURI(Uri.parse(mCurrent.getThumbnail()));
    }

    @Override
    public int getItemCount() {
        return mDaftarLocation.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView LocationNameItemView;
        public final TextView LocationItemView;
        final LocationAdapter mAdapter;

        public LocationViewHolder(@NonNull View itemView, LocationAdapter adapter) {
            super(itemView);
            LocationNameItemView = itemView.findViewById(R.id.locationNameTextView);
            LocationItemView = itemView.findViewById(R.id.locationLocationTextView);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            String name = mDaftarLocation.get(mPosition).getName();
            String location = mDaftarLocation.get(mPosition).getLocation();

//            Toast.makeText(view.getContext(), name + " dipilih", Toast.LENGTH_SHORT).show();
            Intent intentItem = new Intent(view.getContext(), ItemActivity.class);
            intentItem.putExtra("name", name);
            intentItem.putExtra("location", location);
            view.getContext().startActivity(intentItem);
        }
    }
}
