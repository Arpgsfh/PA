package org.d3ifcool.denver;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ProfilAnakActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    DatabaseReference databaseProfilAnak;

    ProfilAnakAdapter mAdapter;
    ConstraintLayout emptyView;
    RecyclerView recyclerView;
    List<ProfilAnak> profilAnaks;

    EditText titleBox, tglLahirBox;
    FloatingActionButton tambah;

    String id;
    int menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_anak);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        id = acct.getId();

        Intent intent = getIntent();
        menu = intent.getIntExtra("MENU",0);

        databaseProfilAnak = FirebaseDatabase.getInstance().getReference("Profil Anak").child(id);

        mAdapter = new ProfilAnakAdapter(this, id, profilAnaks, menu);

        emptyView = (ConstraintLayout) findViewById(R.id.emptyView);
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

                titleBox = new EditText(ProfilAnakActivity.this);
                titleBox.setHint("Nama Anak");
                layout.addView(titleBox); // Notice this is an add method

                tglLahirBox = new EditText(ProfilAnakActivity.this);
                tglLahirBox.setHint("Tanggal Lahir Anak");
                tglLahirBox.setFocusable(false);
                tglLahirBox.isClickable();
                layout.addView(tglLahirBox); // Notice this is an add method

                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }

                };

                tglLahirBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(ProfilAnakActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                builder.setView(layout); // Again this is a set method, not add

                builder.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (titleBox.getText().toString().isEmpty() || tglLahirBox.getText(). toString().isEmpty()){
                            Toast.makeText(ProfilAnakActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            String id = databaseProfilAnak.push().getKey();
                            String nama = titleBox.getText().toString();
                            int hari = myCalendar.get(Calendar.DAY_OF_MONTH);
                            int bulan = myCalendar.get(Calendar.MONTH)+1;
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

        databaseProfilAnak.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profilAnaks.clear();

                for (DataSnapshot profilAnakSnapshot : dataSnapshot.getChildren()){
                    ProfilAnak profilAnak = profilAnakSnapshot.getValue(ProfilAnak.class);
                    profilAnaks.add(profilAnak);
                }

                emptyView.setVisibility(profilAnaks.isEmpty() ? View.VISIBLE : View.GONE);

                if (this != null){
                    ProfilAnakAdapter profilAnakAdapter = new ProfilAnakAdapter(ProfilAnakActivity.this, id, profilAnaks, menu);
                    recyclerView.setAdapter(profilAnakAdapter);
                }

                ProfilAnakAdapter profilAnakAdapter = new ProfilAnakAdapter(ProfilAnakActivity.this, id, profilAnaks, menu);
                recyclerView.setAdapter(profilAnakAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    private void updateLabel() {

        tglLahirBox.setText(sdf.format(myCalendar.getTime()));
    }
}
