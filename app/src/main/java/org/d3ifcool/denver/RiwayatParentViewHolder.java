package org.d3ifcool.denver;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.listeners.OnGroupClickListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class RiwayatParentViewHolder extends GroupViewHolder implements View.OnClickListener {
    CardView cardView;
    ConstraintLayout layout;
    public View button;
    public TextView listGroup;

    private OnGroupClickListener listener;

    public RiwayatParentViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.riwayat_card_view);
        layout = (ConstraintLayout) itemView.findViewById(R.id.layout_riwayat);
        button = (View) itemView.findViewById(R.id.buttonIndicatorRiwayat);
        listGroup = (TextView) itemView.findViewById(R.id.listParentRiwayat);

        itemView.setOnClickListener(this);
    }

    public void setParentTitle(ExpandableGroup group) {
        String kategori=group.getTitle();
        listGroup.setText("Test Bulan ke-"+kategori);


    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        button.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        button.setAnimation(rotate);
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            if (listener.onGroupClick(getAdapterPosition())){
                setExpanded(false);
            }else {
                setExpanded(true);
            }
        }
    }

    public void setExpanded(boolean expanded) {
        RotateAnimation rotateAnimation;
        if (expanded) { // rotate clockwise
            rotateAnimation = new RotateAnimation(0, 180,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        } else { // rotate counterclockwise
            rotateAnimation = new RotateAnimation(180, 0,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        }
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        button.startAnimation(rotateAnimation);
    }

    public void setOnGroupClickListener(OnGroupClickListener listener) {
        this.listener = listener;
    }
}
