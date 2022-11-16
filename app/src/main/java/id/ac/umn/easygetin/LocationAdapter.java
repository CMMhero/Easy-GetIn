package id.ac.umn.easygetin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class LocationAdapter extends FirestoreRecyclerAdapter<Location, LocationAdapter.LocationViewHolder> {

    Context context;

    public LocationAdapter(@NonNull FirestoreRecyclerOptions<Location> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull LocationViewHolder holder, int position, @NonNull Location location) {
        holder.locationNameTV.setText(location.name);
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        return new LocationViewHolder(view);
    }


    class LocationViewHolder extends RecyclerView.ViewHolder{

        TextView locationNameTV;
        ImageView locationIV;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
