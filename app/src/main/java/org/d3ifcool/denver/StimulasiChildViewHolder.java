package org.d3ifcool.denver;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class StimulasiChildViewHolder extends ChildViewHolder {
    public TextView listChildTahapan;
    public TextView listChildStimulasi;

    public StimulasiChildViewHolder(View itemView) {
        super(itemView);
        listChildTahapan = (TextView) itemView.findViewById(R.id.listChildTahapan);
        listChildStimulasi = (TextView) itemView.findViewById(R.id.listChildStimulasi);

    }

    public void onBind(String tahapan, String stimulasi) {
        listChildTahapan.setText(tahapan);
        listChildStimulasi.setText(stimulasi);
    }

}
