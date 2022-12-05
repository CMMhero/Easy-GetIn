package id.ac.umn.easygetin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HistoryAdapter extends FirestoreRecyclerAdapter<Order, HistoryAdapter.HistoryViewHolder>{
    Context context;

    public HistoryAdapter(@NonNull FirestoreRecyclerOptions<Order> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull HistoryViewHolder holder, int position, @NonNull Order model) {
        holder.nameTV.setText(model.locationName);
        holder.nomorParkirTV.setText("Nomor Parkir: " + model.nomorParkir);
        holder.jamMasukTV.setText("Jam Masuk: " + Functions.convertTimeToString(model.start));
        holder.jamKeluarTV.setText("Jam Keluar: " + Functions.convertTimeToString(model.end));
        holder.codeTV.setText("CODE: "+ model.code);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, jamMasukTV, jamKeluarTV, nomorParkirTV, codeTV;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.NameHistory);
            jamMasukTV = itemView.findViewById(R.id.JamMasukHistory);
            jamKeluarTV = itemView.findViewById(R.id.JamKeluarHistory);
            nomorParkirTV = itemView.findViewById(R.id.NoParkirHistory);
            codeTV = itemView.findViewById(R.id.CodeHistory);
        }
    }
}

