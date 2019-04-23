package org.d3ifcool.denver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ProfilAnakAdapter extends RecyclerView.Adapter<ProfilAnakAdapter.ProfilAnakViewHolder> {

    DatabaseReference databaseProfilAnak;

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

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentProfil.getId();
                databaseProfilAnak.child(currentProfil.id).removeValue();
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
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("RELOAD", 1);
                context.startActivity(intent);
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
        public Button deleteButton;

        public ProfilAnakViewHolder(View itemView) {
            super(itemView);

            databaseProfilAnak = FirebaseDatabase.getInstance().getReference("Profil Anak").child(id);

            noTextView = (TextView) itemView.findViewById(R.id.noAnakTextView);
            namaTextView = (TextView) itemView.findViewById(R.id.namaAnakTextView);
            umurTextView = (TextView) itemView.findViewById(R.id.umurTextView);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButton);
        }
    }
}
