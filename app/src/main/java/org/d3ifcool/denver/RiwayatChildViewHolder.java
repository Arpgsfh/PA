package org.d3ifcool.denver;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class RiwayatChildViewHolder extends ChildViewHolder {
    public ConstraintLayout listChildCardView;
    public TextView listChildNama;
    public TextView listChildTanggal;
    public TextView listChildNilai;
    public TextView listChildNK, listChildNH, listChildNB, listChildNS;
    public TextView lihatDetailBtn;

    public RiwayatChildViewHolder(View itemView) {
        super(itemView);
        listChildCardView = (ConstraintLayout) itemView.findViewById(R.id.layoutChildRiwayat);
        listChildNama = (TextView) itemView.findViewById(R.id.list_ket_riwayat);
        listChildTanggal = (TextView) itemView.findViewById(R.id.list_tanggal_riwayat);
        listChildNilai = (TextView) itemView.findViewById(R.id.list_nilai_riwayat);
        listChildNK = (TextView) itemView.findViewById(R.id.skorGerakKasar);
        listChildNH = (TextView) itemView.findViewById(R.id.skorGerakHalus);
        listChildNB = (TextView) itemView.findViewById(R.id.skorBicara);
        listChildNS = (TextView) itemView.findViewById(R.id.skorSosialisasi);
        lihatDetailBtn = (TextView) itemView.findViewById(R.id.lihatDetailBtn);

    }

    public void onBind(String nama, String tanggal, String ket, int nilai, int jumlahSoal, int nKasar, int nHalus, int nBicara, int nSosialisasi, int jKasar, int jHalus, int jBicara, int jSosialisasi) {
        listChildNama.setText(ket);
        listChildTanggal.setText(tanggal);
        listChildNilai.setText(String.valueOf(nilai)+" /"+String.valueOf(jumlahSoal));
        listChildNK.setText(String.valueOf(nKasar+" /"+jKasar));
        listChildNH.setText(String.valueOf(nHalus+" /"+jHalus));
        listChildNB.setText(String.valueOf(nBicara+" /"+jBicara));
        listChildNS.setText(String.valueOf(nSosialisasi+" /"+jSosialisasi));
    }

}
