package org.d3ifcool.denver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String PROFILE = "profile";
    String idProfil;
    String namaProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView namaProfileTextButton = (TextView) findViewById(R.id.namaAnakTextView);
        Button ubahProfileButton = (Button) findViewById(R.id.ubahProfile);
        Button testButton = (Button) findViewById(R.id.testBtn);
        Button stimulasiButton = (Button) findViewById(R.id.stimulasiBtn);
        Button riwayatButton = (Button) findViewById(R.id.riwayatBtn);

        SharedPreferences prefs = getSharedPreferences(PROFILE, MODE_PRIVATE);
        idProfil = prefs.getString("ID", null);
        namaProfile = prefs.getString("NAMA", null);

        namaProfileTextButton.setText(namaProfile);

        ubahProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(MainActivity.this, ProfilAnakActivity.class);
                startActivity(testIntent);
                finish();
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(MainActivity.this, PilihUmurActivity.class);
                testIntent.putExtra("MENU",1);
                startActivity(testIntent);
            }
        });

        stimulasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stimulasiIntent = new Intent(MainActivity.this, PilihUmurActivity.class);
                stimulasiIntent.putExtra("MENU",2);
                startActivity(stimulasiIntent);
            }
        });

        riwayatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent riwayatIntent = new Intent(MainActivity.this, RiwayatActivity.class);
                startActivity(riwayatIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (idProfil==null){
            Intent intentProfil = new Intent(MainActivity.this, ProfilAnakActivity.class);
            startActivity(intentProfil);
            finish();
        }
    }
}
