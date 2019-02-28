package org.d3ifcool.denver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button testButton = (Button) findViewById(R.id.testBtn);
        Button stimulasiButton = (Button) findViewById(R.id.stimulasiBtn);
        Button riwayatButton = (Button) findViewById(R.id.riwayatBtn);

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
}
