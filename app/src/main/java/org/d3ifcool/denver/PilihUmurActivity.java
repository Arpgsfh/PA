package org.d3ifcool.denver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PilihUmurActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    FirebaseDatabase database;

    private String[] mThumbIds = new String[]{
            "3", "6", "9", "12",
            "15", "18", "21", "24",
            "30", "36", "42", "48",
            "54", "60", "66", "72"
    };

    List<String> mThumbs = Arrays.asList(
            "3", "6", "9", "12",
            "15", "18", "21", "24",
            "30", "36", "42", "48",
            "54", "60", "66", "72");

    List<Integer> lulusList = Arrays.asList(
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0);

    public static final String PROFILE = "profile";
    String idProfil;
    String namaProfile;
    int tahun, bulan, hari;
    int years = 0;
    int months = 0;
    int days = 0;

    int menu;
    
    GridView gridview;

    Boolean umur;

    String idAkun;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_umur);

        ConstraintLayout profilAnak = (ConstraintLayout) findViewById(R.id.profilAnak);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        idAkun = acct.getId();

        database = FirebaseDatabase.getInstance();

        loadProfilPreferences();

        Intent intent = getIntent();
        menu = intent.getIntExtra("MENU",0);

        TextView textView9 = (TextView) findViewById(R.id.textView9);
        if (menu==1){
            textView9.setText("Pilih umur skrining");
        }else if (menu==2){
            textView9.setText("Pilih umur stimulasi");
        }
        gridview = (GridView) findViewById(R.id.gridView);

        loadDatabase();
    }

    public void loadDatabase(){
        DatabaseReference databaseUmur = database.getReference("Umur").child(idAkun).child(idProfil);

        databaseUmur.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int pass;
                for (DataSnapshot profilAnakSnapshot : dataSnapshot.getChildren()){
                    pass = profilAnakSnapshot.getValue(Integer.class);
                    if (pass == 1 || pass == 2){
                        lulusList.set(mThumbs.indexOf(profilAnakSnapshot.getKey()), pass);
                    }
                }

                PilihUmurAdapter pilihUmurAdapter = new PilihUmurAdapter(PilihUmurActivity.this, months, menu, lulusList);
                gridview.setAdapter(pilihUmurAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void AgeCalculator(){
        Calendar now = Calendar.getInstance();
        years = now.get(Calendar.YEAR) - tahun;
        months = now.get(Calendar.MONTH) - bulan;

        if (months<0){
            years--;
            months = 12 + months;
        }
        months = (years*12) + months;
        days = now.get(Calendar.DAY_OF_MONTH) - hari;

        if (days<=0){
            months++;
        }
    }

    public void loadProfilPreferences(){
        SharedPreferences preferences = getSharedPreferences(PROFILE, MODE_PRIVATE);
        TextView namaAnak = (TextView) findViewById(R.id.namaAnakTextView);
        TextView umurAnak = (TextView) findViewById(R.id.umurTextView);

        idProfil = preferences.getString("ID", null);
        namaProfile = preferences.getString("NAMA", null);
        tahun = preferences.getInt("TAHUN", 0);
        bulan = preferences.getInt("BULAN", 0);
        hari = preferences.getInt("HARI", 0);

        namaAnak.setText(namaProfile);
        AgeCalculator();
        umurAnak.setText("Usia "+months+" bulan");

        gridview = (GridView) findViewById(R.id.gridView);
        loadDatabase();

        preferences.registerOnSharedPreferenceChangeListener(PilihUmurActivity.this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadProfilPreferences();
    }
}
