package org.d3ifcool.denver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    private Context context;
    private List<Riwayat> riwayatList;

    public RiwayatAdapter() {
    }

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

        int nilai = currentRiwayat.getNilai();

        if (nilai>8){
            holder.nilaiTextView.setText("Sesuai");
        }else if (nilai>6){
            holder.nilaiTextView.setText("Meragukan");
        }else {
            holder.nilaiTextView.setText("Menyimpang");
        }
        holder.namaTextView.setText(currentRiwayat.getNama());
        holder.umurTextView.setText("bulan ke- "+currentRiwayat.getUmur());
        holder.tanggalTextView.setText(currentRiwayat.getTanggal());
    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public TextView namaTextView;
        public TextView umurTextView;
        public TextView nilaiTextView;
        public TextView tanggalTextView;

        public RiwayatViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.riwayat_card_view);
            namaTextView = (TextView) itemView.findViewById(R.id.list_ket_riwayat);
            umurTextView = (TextView) itemView.findViewById(R.id.list_umur_riwayat);
            nilaiTextView = (TextView) itemView.findViewById(R.id.list_nilai_riwayat);
            tanggalTextView = (TextView) itemView.findViewById(R.id.list_tanggal_riwayat);
        }
    }
}
