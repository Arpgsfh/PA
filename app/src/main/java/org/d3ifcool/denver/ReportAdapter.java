package org.d3ifcool.denver;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Repo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private Context context;
    private List<Report> reportList;

    public ReportAdapter(Context context, List<Report> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_report, parent, false);
        return  new ReportAdapter.ReportViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        final  Report currentReport = reportList.get(position);
        holder.noTextView.setText(String.valueOf(position+1));
        holder.pertanyaanTextView.setText(currentReport.rPertanyaan);

        switch (currentReport.rJawaban){
            case 0:
                holder.jawabanImageView.setImageResource(R.mipmap.ic_cross);
                break;
            case 1:
                holder.jawabanImageView.setImageResource(R.mipmap.ic_check);

        }

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }


    public class ReportViewHolder extends RecyclerView.ViewHolder{

        public TextView noTextView;
        public TextView pertanyaanTextView;
        public ImageView jawabanImageView;

        public ReportViewHolder(View itemView) {
            super(itemView);

            noTextView = (TextView) itemView.findViewById(R.id.list_no_report);
            pertanyaanTextView = (TextView) itemView.findViewById(R.id.list_pertanyaan_report);
            jawabanImageView = (ImageView) itemView.findViewById(R.id.list_jawaban_report);
        }
    }
}
