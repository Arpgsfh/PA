package org.d3ifcool.denver;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ProfilAnakAdapter extends RecyclerView.Adapter<ProfilAnakAdapter.ProfilAnakViewHolder> {

    DatabaseReference databaseProfilAnak;
    DatabaseReference databaseRiwayat;

    final Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    EditText titleBox, tglLahirBox;

    private Context context;
    private String id;
    private List<ProfilAnak> profilAnakList;
    public static final String PROFILE = "profile";

    public ProfilAnakAdapter(Context context, String id, List<ProfilAnak> profilAnakList) {
        this.context = context;
        this.id = id;
        this.profilAnakList = profilAnakList;
    }

    @NonNull
    @Override
    public ProfilAnakViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_profil_anak, parent, false);
        return  new ProfilAnakAdapter.ProfilAnakViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilAnakViewHolder holder, int position) {
        final  ProfilAnak currentProfil = profilAnakList.get(position);
        holder.noTextView.setText(Integer.toString(position+1));
        holder.namaTextView.setText(currentProfil.getNama());
        holder.umurTextView.setText(currentProfil.getTglLahir().hari+" "+currentProfil.getTglLahir().bulan+" "+currentProfil.getTglLahir().tahun);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit Anak");

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                titleBox = new EditText(context);
                titleBox.setHint("Nama Anak");
                titleBox.setText(currentProfil.getNama());
                layout.addView(titleBox); // Notice this is an add method

                tglLahirBox = new EditText(context);
                tglLahirBox.setHint("Tanggal Lahir Anak");
                tglLahirBox.setFocusable(false);
                tglLahirBox.isClickable();
                myCalendar.set(Calendar.YEAR, currentProfil.getTglLahir().tahun);
                myCalendar.set(Calendar.MONTH, currentProfil.getTglLahir().bulan);
                myCalendar.set(Calendar.DAY_OF_MONTH, currentProfil.getTglLahir().hari);
                tglLahirBox.setText(sdf.format(myCalendar.getTime()));
                layout.addView(tglLahirBox); // Notice this is an add method

                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        tglLahirBox.setText(sdf.format(myCalendar.getTime()));
                    }

                };

                tglLahirBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(context, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                builder.setView(layout); // Again this is a set method, not add

                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (titleBox.getText().toString().isEmpty() || tglLahirBox.getText(). toString().isEmpty()){
                            Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            String id = currentProfil.getId();
                            String nama = titleBox.getText().toString();
                            int hari = myCalendar.get(Calendar.DAY_OF_MONTH);
                            int bulan = myCalendar.get(Calendar.MONTH);
                            int tahun = myCalendar.get(Calendar.YEAR);

                            Umur umur = new Umur(hari, bulan, tahun);

                            ProfilAnak profilAnak = new ProfilAnak(id, nama, umur);
                            databaseProfilAnak.child(id).setValue(profilAnak);
                        }
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentProfil.getId();
                databaseProfilAnak.child(currentProfil.id).removeValue();
                databaseRiwayat.child(currentProfil.id).removeValue();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = context.getSharedPreferences(PROFILE, Context.MODE_PRIVATE).edit();
                editor.putString("ID", currentProfil.getId());
                editor.putString("NAMA", currentProfil.getNama());
                editor.putInt("TAHUN", currentProfil.getTglLahir().tahun);
                editor.putInt("BULAN", currentProfil.getTglLahir().bulan);
                editor.putInt("HARI", currentProfil.getTglLahir().hari);
                editor.commit();
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return profilAnakList.size();
    }


    public class ProfilAnakViewHolder extends RecyclerView.ViewHolder{

        public TextView noTextView;
        public TextView namaTextView;
        public TextView umurTextView;
        public Button editButton;
        public Button deleteButton;

        public ProfilAnakViewHolder(View itemView) {
            super(itemView);

            databaseProfilAnak = FirebaseDatabase.getInstance().getReference("Profil Anak").child(id);
            databaseRiwayat = FirebaseDatabase.getInstance().getReference("Riwayat").child(id);

            noTextView = (TextView) itemView.findViewById(R.id.noAnakTextView);
            namaTextView = (TextView) itemView.findViewById(R.id.namaAnakTextView);
            umurTextView = (TextView) itemView.findViewById(R.id.umurTextView);
            editButton = (Button) itemView.findViewById(R.id.editButton);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButton);
        }
    }
}
