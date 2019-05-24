package org.d3ifcool.denver;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class RiwayatParent extends ExpandableGroup<RiwayatChild> {



    public RiwayatParent(String title, List<RiwayatChild> items) {
        super(title, items);
    }

}
