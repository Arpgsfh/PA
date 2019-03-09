package org.d3ifcool.denver;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StimulasiAdapter extends RecyclerView.Adapter<StimulasiAdapter.StimulasiViewHolder> {

    private Context context;
    private List<Stimulasi> stimulasiList;

    public StimulasiAdapter(Context context, List<Stimulasi> stimulasiList) {
        this.context = context;
        this.stimulasiList = stimulasiList;
    }

    @NonNull
    @Override
    public StimulasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_stimulasi, parent, false);
        return  new StimulasiViewHolder(listItemView);

    }

    @Override
    public void onBindViewHolder(@NonNull StimulasiViewHolder holder, int position) {
        final  Stimulasi currentStimulasi = stimulasiList.get(position);

        holder.tahapanTextView.setText(currentStimulasi.getTahapan());
        holder.stimulasiTextView.setText(currentStimulasi.getStimulasi());
        holder.jenisTextView.setText(currentStimulasi.getJenis());

    }

    @Override
    public int getItemCount() {
        return stimulasiList.size();
    }

    public class StimulasiViewHolder extends RecyclerView.ViewHolder {

        public TextView tahapanTextView;
        public TextView stimulasiTextView;
        public TextView jenisTextView;

        public StimulasiViewHolder(View itemView) {
            super(itemView);

            tahapanTextView = (TextView) itemView.findViewById(R.id.list_tahapan);
            stimulasiTextView = (TextView) itemView.findViewById(R.id.list_stimulasi);
            jenisTextView = (TextView) itemView.findViewById(R.id.list_jenis);
        }
    }
}
