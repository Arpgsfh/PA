package org.d3ifcool.denver;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User on 2/7/2019.
 */

public class PilihUmurAdapter extends BaseAdapter {
    private Context mContext;
    private int months;
    int menu;

    public PilihUmurAdapter() {
    }

    public PilihUmurAdapter(Context mContext, int months, int menu) {
        this.mContext = mContext;
        this.months = months;
        this.menu = menu;
    }

    public int getMonths() {
        return months;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        grid = inflater.inflate(R.layout.gridview_item, null);
        TextView textView = (TextView) grid.findViewById(R.id.judul);

        if (Integer.valueOf(mThumbIds[position])>months){
            grid.setBackground(mContext.getResources().getDrawable(R.drawable.grid_bg_non));

        }else {
            grid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menu==1){
                        Intent intentTest = new Intent(mContext, TestActivity.class);
                        intentTest.putExtra("UMUR",Integer.valueOf(mThumbIds[position]));
                        mContext.startActivity(intentTest);
                    }else if (menu==2){
                        Intent intentStimulasi = new Intent(mContext, StimulasiActivity.class);
                        intentStimulasi.putExtra("UMUR",Integer.valueOf(mThumbIds[position]));
                        mContext.startActivity(intentStimulasi);
                    }
                }
            });
        }

        textView.setText(mThumbIds[position]);

        return grid;
    }

    private String[] mThumbIds = {
            "3", "6",
            "9", "12",
            "15", "18"
    };
}
