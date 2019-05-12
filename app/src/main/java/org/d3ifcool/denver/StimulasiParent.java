package org.d3ifcool.denver;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class StimulasiParent extends ExpandableGroup<StimulasiChild> {


    public StimulasiParent(String title, List<StimulasiChild> items) {
        super(title, items);
    }

}
