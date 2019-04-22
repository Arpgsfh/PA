package org.d3ifcool.denver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PenilaianActivity extends AppCompatActivity {

    public static final String PROFILE = "profile";
    String idProfil;
    String namaProfile;

    CardView penilaianCardView;
    TextView skor;
    TextView skorHalus;
    TextView skorKasar;
    TextView skorBicara;
    TextView skorSosialisasi;
    TextView penilaian;

    FirebaseDatabase database;

    Intent intent1;

    int umur, nilai;
    int jKasar, jHalus, jBicara, jSosialisasi;
    int nKasar, nHalus, nBicara, nSosialisasi;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penilaian);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        id = acct.getId();

        database = FirebaseDatabase.getInstance();

        SharedPreferences prefs = getSharedPreferences(PROFILE, MODE_PRIVATE);
        idProfil = prefs.getString("ID", null);
        namaProfile = prefs.getString("NAMA", null);

        final Intent intent = getIntent();
        nilai = intent.getIntExtra("NILAI",0);
        nKasar = intent.getIntExtra("NK",0);
        nHalus = intent.getIntExtra("NH",0);
        nBicara = intent.getIntExtra("NB",0);
        nSosialisasi = intent.getIntExtra("NS",0);

        jKasar = intent.getIntExtra("JK",0);
        jHalus = intent.getIntExtra("JH",0);
        jBicara = intent.getIntExtra("JB",0);
        jSosialisasi = intent.getIntExtra("JS",0);

        umur = intent.getIntExtra("UMUR",0);

        penilaianCardView = (CardView) findViewById(R.id.penilaian_card_view);
        skor = (TextView) findViewById(R.id.skor_penilaian);
        skorKasar = (TextView) findViewById(R.id.skorGerakKasar);
        skorHalus = (TextView) findViewById(R.id.skorGerakHalus);
        skorBicara = (TextView) findViewById(R.id.skorBicara);
        skorSosialisasi = (TextView) findViewById(R.id.skorSosialisasi);
        penilaian = (TextView) findViewById(R.id.ket_penilaian);

        Button button = (Button) findViewById(R.id.button);

        skor.setText(nilai+" /10");
        skorKasar.setText(nKasar+" /"+jKasar);
        skorHalus.setText(nHalus+" /"+jHalus);
        skorBicara.setText(nBicara+" /"+jBicara);
        skorSosialisasi.setText(nSosialisasi+" /"+jSosialisasi);

        if (nilai>8){
            penilaianCardView.setCardBackgroundColor(getResources().getColor(R.color.green));
            penilaian.setText("Selamat, perkembangan anak Anda Sesuai.");
            button.setText("Lanjut");
            intent1 = new Intent(PenilaianActivity.this, MainActivity.class);
        }else if (nilai>6){
            penilaianCardView.setCardBackgroundColor(getResources().getColor(R.color.yellow));
            penilaian.setText("Pantau terus perkembangan anak Anda. Cari Kemungkinan Penyakit, ulangi test 2 Minggu lagi. Jika masih Meragukan, segera rujuk ke RS atau Poli Anak");
            intent1 = new Intent(PenilaianActivity.this, StimulasiActivity.class);
            intent1.putExtra("UMUR",umur);
        }else{
            penilaianCardView.setCardBackgroundColor(getResources().getColor(R.color.red));
            penilaian.setText("Ada penyimpangan dalam perkembangan anak Anda, segera rujuk ke RS atau Poli Anak");
            intent1 = new Intent(PenilaianActivity.this, StimulasiActivity.class);
            intent1.putExtra("UMUR",umur);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                DatabaseReference myRef = database.getReference("Riwayat").child(id).child(idProfil);
                Riwayat riwayat = new Riwayat(namaProfile, date, String.valueOf(umur), nilai);
                String id = myRef.push().getKey();
                myRef.child(id).setValue(riwayat);

                startActivity(intent1);
                finish();
            }
        });

    }
}
