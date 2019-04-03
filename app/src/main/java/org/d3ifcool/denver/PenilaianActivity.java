package org.d3ifcool.denver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PenilaianActivity extends AppCompatActivity {

    public static final String PROFILE = "profile";

    TextView penilaian;

    FirebaseDatabase database;

    Intent intent1;

    String nama;
    int umur, nilai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penilaian);

        database = FirebaseDatabase.getInstance();

        SharedPreferences prefs = getSharedPreferences(PROFILE, MODE_PRIVATE);
        nama = prefs.getString("NAMA", null);

        final Intent intent = getIntent();
        nilai = intent.getIntExtra("NILAI",0);
        umur = intent.getIntExtra("UMUR",0);
        penilaian = (TextView) findViewById(R.id.penilaian);

        Button button = (Button) findViewById(R.id.button);

        if (nilai>8){
            penilaian.setText("Selamat, perkembangan anak Anda Sesuai.");
            button.setText("Lanjut");
            intent1 = new Intent(PenilaianActivity.this, MainActivity.class);
        }else if (nilai>6){
            penilaian.setText("Pantau terus perkembangan anak Anda. Cari Kemungkinan Penyakit, ulangi test 2 Minggu lagi. Jika masih Meragukan, segera rujuk ke RS atau Poli Anak");
            intent1 = new Intent(PenilaianActivity.this, StimulasiActivity.class);
            intent1.putExtra("UMUR",umur);
        }else{
            penilaian.setText("Ada penyimpangan dalam perkembangan anak Anda, segera rujuk ke RS atau Poli Anak");
            intent1 = new Intent(PenilaianActivity.this, StimulasiActivity.class);
            intent1.putExtra("UMUR",umur);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                DatabaseReference myRef = database.getReference("Riwayat");
                Riwayat riwayat = new Riwayat(nama, date, String.valueOf(umur), nilai);
                String id = myRef.push().getKey();
                myRef.child(id).setValue(riwayat);

                startActivity(intent1);
                finish();
            }
        });

    }
}
