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

import java.text.DateFormat;
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
    int tKasar=0, tHalus=0, tBicara=0, tSosialisasi=0;

    int jumlahSoal;
    String[] rPertanyaan;
    int[] rJawaban;
    boolean lulus = false;

    String idAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penilaian);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        idAkun = acct.getId();

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

        jumlahSoal = intent.getIntExtra("JP", 0);
        rPertanyaan = intent.getStringArrayExtra("RP");
        rJawaban = intent.getIntArrayExtra("RJ");

        penilaianCardView = (CardView) findViewById(R.id.penilaian_card_view);
        skor = (TextView) findViewById(R.id.skor_penilaian);
        skorKasar = (TextView) findViewById(R.id.skorGerakKasar);
        skorHalus = (TextView) findViewById(R.id.skorGerakHalus);
        skorBicara = (TextView) findViewById(R.id.skorBicara);
        skorSosialisasi = (TextView) findViewById(R.id.skorSosialisasi);
        penilaian = (TextView) findViewById(R.id.list_ket_riwayat);

        Button button = (Button) findViewById(R.id.button);

        skor.setText(nilai+" /"+jumlahSoal);
        skorKasar.setText(nKasar+" /"+jKasar);
        skorHalus.setText(nHalus+" /"+jHalus);
        skorBicara.setText(nBicara+" /"+jBicara);
        skorSosialisasi.setText(nSosialisasi+" /"+jSosialisasi);

        if (nKasar == jKasar){
            tKasar = 1;
        }
        if (nHalus == jHalus){
            tHalus = 1;
        }
        if (nBicara == jBicara){
            tBicara = 1;
        }
        if (nSosialisasi == jSosialisasi){
            tSosialisasi = 1;
        }

        if (nilai>8){
            lulus=true;
            penilaianCardView.setCardBackgroundColor(getResources().getColor(R.color.green));
            penilaian.setText("Selamat, perkembangan anak Anda Sesuai.");
            button.setText("Lanjut");
            intent1 = new Intent(PenilaianActivity.this, MainActivity.class);
        }else if (nilai>6){
            penilaianCardView.setCardBackgroundColor(getResources().getColor(R.color.yellow));
            penilaian.setText("Pantau terus perkembangan anak Anda. Cari Kemungkinan Penyakit, ulangi test 2 Minggu lagi. Jika masih Meragukan, segera rujuk ke RS atau Poli Anak");
            intent1 = new Intent(PenilaianActivity.this, StimulasiActivity.class);
            intent1.putExtra("UMUR",umur);
            intent1.putExtra("GK",tKasar);
            intent1.putExtra("GH",tHalus);
            intent1.putExtra("BB",tBicara);
            intent1.putExtra("SK",tSosialisasi);
        }else{
            penilaianCardView.setCardBackgroundColor(getResources().getColor(R.color.red));
            penilaian.setText("Ada penyimpangan dalam perkembangan anak Anda, segera rujuk ke RS atau Poli Anak");
            intent1 = new Intent(PenilaianActivity.this, StimulasiActivity.class);
            intent1.putExtra("UMUR",umur);
            intent1.putExtra("GK",tKasar);
            intent1.putExtra("GH",tHalus);
            intent1.putExtra("BB",tBicara);
            intent1.putExtra("SK",tSosialisasi);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tanggal = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
                String ket = penilaian.getText().toString();

                DatabaseReference myRef = database.getReference("Riwayat").child(idAkun).child(idProfil).child(String.valueOf(umur));
                String id = myRef.push().getKey();
                RiwayatChild riwayat = new RiwayatChild(id, namaProfile, tanggal, ket, nilai, jumlahSoal, nKasar, nHalus, nBicara, nSosialisasi, jKasar, jHalus, jBicara, jSosialisasi);
                myRef.child(id).setValue(riwayat);

                DatabaseReference databaseDetail = database.getReference("Detail").child(idAkun).child(idProfil).child(id);
                for (int i=0; i< jumlahSoal; i++){
                    Report report = new Report(rPertanyaan[i], rJawaban[i]);
                    databaseDetail.child(String.valueOf(i)).setValue(report);
                }

                DatabaseReference databaseUmur = database.getReference("Umur").child(idAkun).child(idProfil).child(String.valueOf(umur));
                databaseUmur.setValue(lulus);

                startActivity(intent1);
                finish();
            }
        });

    }
}
