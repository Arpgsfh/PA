package org.d3ifcool.denver;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class RiwayatChildViewHolder extends ChildViewHolder {
    public CardView listChildCardView;
    public TextView listChildNama;
    public TextView listChildTanggal;
    public TextView listChildNilai;
    public TextView listChildNK, listChildNH, listChildNB, listChildNS;

    public RiwayatChildViewHolder(View itemView) {
        super(itemView);
        listChildCardView = (CardView) itemView.findViewById(R.id.penilaian_card_view);
        listChildNama = (TextView) itemView.findViewById(R.id.list_nama_riwayat);
        listChildTanggal = (TextView) itemView.findViewById(R.id.list_tanggal_riwayat);
        listChildNilai = (TextView) itemView.findViewById(R.id.list_nilai_riwayat);
        listChildNK = (TextView) itemView.findViewById(R.id.skorGerakKasar);
        listChildNH = (TextView) itemView.findViewById(R.id.skorGerakHalus);
        listChildNB = (TextView) itemView.findViewById(R.id.skorBicara);
        listChildNS = (TextView) itemView.findViewById(R.id.skorSosialisasi);

    }

    public void onBind(String nama, String tanggal, int nilai, int nKasar, int nHalus, int nBicara, int nSosialisasi, int jKasar, int jHalus, int jBicara, int jSosialisasi) {
        listChildNama.setText(nama);
        listChildTanggal.setText(tanggal);
        listChildNilai.setText(String.valueOf(nilai)+" /10");
        listChildNK.setText(String.valueOf(nKasar+" /"+jKasar));
        listChildNH.setText(String.valueOf(nHalus+" /"+jHalus));
        listChildNB.setText(String.valueOf(nBicara+" /"+jBicara));
        listChildNS.setText(String.valueOf(nSosialisasi+" /"+jSosialisasi));
    }

}
