package org.d3ifcool.denver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class PilihUmurActivity extends AppCompatActivity {
    int[] mThumbIds = {
            3, 6,
            9, 12,
            15, 18
    };

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
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_umur);

        TextView namaAnak = (TextView) findViewById(R.id.namaAnakTextView);
        TextView umurAnak = (TextView) findViewById(R.id.umurTextView);

        SharedPreferences prefs = getSharedPreferences(PROFILE, MODE_PRIVATE);
        idProfil = prefs.getString("ID", null);
        namaProfile = prefs.getString("NAMA", null);
        tahun = prefs.getInt("TAHUN", 0);
        bulan = prefs.getInt("BULAN", 0);
        hari = prefs.getInt("HARI", 0);

        namaAnak.setText(namaProfile);
        AgeCalculator();
        umurAnak.setText(months+" Bulan");

        Intent intent = getIntent();
        menu = intent.getIntExtra("MENU",0);

        gridview = (GridView) findViewById(R.id.gridView);

        gridview.setAdapter(new PilihUmurAdapter(this, months, menu));

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
    
    public void cekUmur(){
        for (int i=0; i>=5; i++){
            if (mThumbIds[i]<= months){
                umur=true;
            }
        }
        
    }
}
