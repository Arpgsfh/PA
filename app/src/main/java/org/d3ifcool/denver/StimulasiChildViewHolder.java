package org.d3ifcool.denver;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class StimulasiChildViewHolder extends ChildViewHolder {
    public LinearLayout layoutChildStimulasi;
    public TextView listChildTahapan;
    public TextView listChildStimulasi;

    public StimulasiChildViewHolder(View itemView) {
        super(itemView);
        layoutChildStimulasi = (LinearLayout) itemView.findViewById(R.id.layout_item_child_stimulasi);
        listChildTahapan = (TextView) itemView.findViewById(R.id.listChildTahapan);
        listChildStimulasi = (TextView) itemView.findViewById(R.id.listChildStimulasi);

    }

    public void onBind(String tahapan, String stimulasi) {
        listChildTahapan.setText(tahapan);
        listChildStimulasi.setText(stimulasi.replace("_b","\n"));
    }

}
