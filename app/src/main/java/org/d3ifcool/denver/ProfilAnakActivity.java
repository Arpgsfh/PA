package org.d3ifcool.denver;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfilAnakActivity extends AppCompatActivity {

    DatabaseReference databaseProfilAnak;

    ProfilAnakAdapter mAdapter;
    RecyclerView recyclerView;
    List<ProfilAnak> profilAnaks;

    FloatingActionButton tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_anak);


        databaseProfilAnak = FirebaseDatabase.getInstance().getReference("Profil Anak");

        mAdapter = new ProfilAnakAdapter(this, profilAnaks);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerProfil);
        tambah = (FloatingActionButton) findViewById(R.id.tambahProfil);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        profilAnaks = new ArrayList<>();

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilAnakActivity.this);
                builder.setTitle("Tambah Anak");

                LinearLayout layout = new LinearLayout(ProfilAnakActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText titleBox = new EditText(ProfilAnakActivity.this);
                titleBox.setHint("Nama");
                layout.addView(titleBox); // Notice this is an add method

                final DatePicker tglLahir = new DatePicker(ProfilAnakActivity.this);
                layout.addView(tglLahir); // Another add method

                builder.setView(layout); // Again this is a set method, not add

                builder.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProfilAnakActivity.this, titleBox.getText().toString(), Toast.LENGTH_SHORT).show();
                        String id = databaseProfilAnak.push().getKey();
                        String nama = titleBox.getText().toString();

                        final Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        calendar.set(tglLahir.getYear(), tglLahir.getMonth(), tglLahir.getDayOfMonth());
                        String tanggal = sdf.format(calendar.getTime());
                        Umur umur = new Umur(tglLahir.getDayOfMonth(), tglLahir.getMonth()+1, tglLahir.getYear());

                        ProfilAnak profilAnak = new ProfilAnak(id, nama, umur);
                        databaseProfilAnak.child(id).setValue(profilAnak);
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

        databaseProfilAnak.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                profilAnaks.clear();

                for (DataSnapshot profilAnakSnapshot : dataSnapshot.getChildren()){
                    ProfilAnak profilAnak = profilAnakSnapshot.getValue(ProfilAnak.class);
                    profilAnaks.add(profilAnak);
                }

                if (this != null){
                    ProfilAnakAdapter profilAnakAdapter = new ProfilAnakAdapter(ProfilAnakActivity.this, profilAnaks);
                    recyclerView.setAdapter(profilAnakAdapter);
                }

                ProfilAnakAdapter profilAnakAdapter = new ProfilAnakAdapter(ProfilAnakActivity.this, profilAnaks);
                recyclerView.setAdapter(profilAnakAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
}
