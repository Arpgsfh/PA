package org.d3ifcool.denver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    private Context context;
    private List<Riwayat> riwayatList;

    public RiwayatAdapter(Context context, List<Riwayat> riwayatList) {
        this.context = context;
        this.riwayatList = riwayatList;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_riwayat, parent, false);
        return  new RiwayatViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {
        final  Riwayat currentRiwayat = riwayatList.get(position);

        holder.umurTextView.setText(currentRiwayat.getUmur());
        holder.nilaiTextView.setText(currentRiwayat.getNilai());
    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder{

        public TextView umurTextView;
        public TextView nilaiTextView;

        public RiwayatViewHolder(View itemView) {
            super(itemView);

            umurTextView = (TextView) itemView.findViewById(R.id.list_umur_riwayat);
            nilaiTextView = (TextView) itemView.findViewById(R.id.list_nilai_riwayat);
        }
    }
}
