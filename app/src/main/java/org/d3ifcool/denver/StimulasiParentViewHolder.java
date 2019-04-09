package org.d3ifcool.denver;

import android.view.View;
import android.widget.TextView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class StimulasiParentViewHolder extends GroupViewHolder {
    public ExpandableCardView listGroup;

    public StimulasiParentViewHolder(View itemView) {
        super(itemView);
        listGroup = (ExpandableCardView) itemView.findViewById(R.id.listParent);
    }

    public void setParentTitle(ExpandableGroup group) {
        listGroup.setTitle(group.getTitle());
    }
}
