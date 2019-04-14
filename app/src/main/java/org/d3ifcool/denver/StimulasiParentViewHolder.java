package org.d3ifcool.denver;

import android.support.v7.widget.CardView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class StimulasiParentViewHolder extends GroupViewHolder {
    CardView cardView;
    public View button;
    public TextView listGroup;
    public SparseBooleanArray expandState = new SparseBooleanArray();

    public StimulasiParentViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.stimulasi_card_view);
        button = (View) itemView.findViewById(R.id.button);
        listGroup = (TextView) itemView.findViewById(R.id.listParent);
    }

    public void setParentTitle(ExpandableGroup group) {
        String kategori=group.getTitle();
        if (kategori.equalsIgnoreCase("1")){
            cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.green));
            listGroup.setText("Gerak Kasar");
        }else if (kategori.equalsIgnoreCase("2")){
            cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.blue));
            listGroup.setText("Gerak Halus");
        }else if (kategori.equalsIgnoreCase("3")){
            cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.red));
            listGroup.setText("Bicara dan Bahasa");
        }else {
            cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.yellow));
            listGroup.setText("Sosialisasi dan Kemandirian");
        }

    }
}
