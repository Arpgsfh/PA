package org.d3ifcool.denver;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class StimulasiChildViewHolder extends ChildViewHolder {
    public LinearLayout layoutChildStimulasi;
    public ImageView listChildImage;
    public TextView listChildTahapan;
    public TextView listChildStimulasi;

    public StimulasiChildViewHolder(View itemView) {
        super(itemView);
        layoutChildStimulasi = (LinearLayout) itemView.findViewById(R.id.layout_item_child_stimulasi);
        listChildImage = (ImageView) itemView.findViewById(R.id.listChildImage);
        listChildTahapan = (TextView) itemView.findViewById(R.id.listChildTahapan);
        listChildStimulasi = (TextView) itemView.findViewById(R.id.listChildStimulasi);

    }

    public void onBind(String tahapan, String stimulasi, String imageUrl) {
        listChildTahapan.setText(tahapan);
        listChildStimulasi.setText(stimulasi.replace("_b","\n"));

        if (imageUrl!=null){
            listChildImage.setVisibility(View.VISIBLE);
            Glide
                    .with(itemView.getContext())
                    .load(imageUrl)
                    .into(listChildImage);
        }else {
            listChildImage.setVisibility(View.GONE);
        }
    }

}
