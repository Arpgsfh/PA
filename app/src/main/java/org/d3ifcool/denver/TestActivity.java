package org.d3ifcool.denver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TestActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    TextView textViewRefresh;
    TextView textViewKoneksi;
    TextView textViewKategori;
    CardView cardView;
    TextView noPertanyaan;
    TextView pertanyaan;
    ImageView gmbr;
    Button btnIya;
    Button btnTidak;

    FirebaseDatabase database;

    int umur;
    int nomor=0;
    int nilai=0;
    int kategori=0;
    int jKasar, jHalus, jBicara, jSosialisasi;
    int nKasar, nHalus, nBicara, nSosialisasi;

    String rPertanyaan[] = new String[10];
    int rJawaban[] = new int[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        umur = intent.getIntExtra("UMUR",0);

        setPertanyaan(nomor);

        mProgressBar = (ProgressBar) findViewById(R.id.loadingBar);
        textViewRefresh = (TextView) findViewById(R.id.textViewRefresh);
        textViewKoneksi = (TextView) findViewById(R.id.koneksi);
        textViewKategori = (TextView) findViewById(R.id.kategori);
        cardView = (CardView) findViewById(R.id.cardView);
        noPertanyaan = (TextView) findViewById(R.id.noPertanyaan);
        pertanyaan = (TextView) findViewById(R.id.pertanyaan);
        gmbr = (ImageView) findViewById(R.id.gmbrPertanyaan);
        btnIya = (Button) findViewById(R.id.buttonIya);
        btnTidak = (Button) findViewById(R.id.buttonTdk);

        btnIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (kategori){
                    case 1:
                        nKasar++;
                        rPertanyaan[nomor-1] = pertanyaan.getText().toString();
                        rJawaban[nomor-1] = 1;
                        break;
                    case 2:
                        nHalus++;
                        rPertanyaan[nomor-1] = pertanyaan.getText().toString();
                        rJawaban[nomor-1] = 1;
                        break;
                    case 3:
                        nBicara++;
                        rPertanyaan[nomor-1] = pertanyaan.getText().toString();
                        rJawaban[nomor-1] = 1;
                        break;
                    case 4:
                        nSosialisasi++;
                        rPertanyaan[nomor-1] = pertanyaan.getText().toString();
                        rJawaban[nomor-1] = 1;
                        break;
                }

                reset();
                nomor++;
                nilai++;

                if (cekKoneksi()){
                    textViewRefresh.setVisibility(View.GONE);
                    textViewKoneksi.setVisibility(View.GONE);
                    setPertanyaan(nomor);
                }else {
                    textViewRefresh.setVisibility(View.VISIBLE);
                    textViewKoneksi.setVisibility(View.VISIBLE);
                }
            }
        });

        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nomor != 0){
                    rPertanyaan[nomor-1] = pertanyaan.getText().toString();
                    rJawaban[nomor-1] = 0;
                }
                reset();
                nomor++;

                if (cekKoneksi()){
                    textViewRefresh.setVisibility(View.GONE);
                    textViewKoneksi.setVisibility(View.GONE);
                    setPertanyaan(nomor);
                }else {
                    textViewRefresh.setVisibility(View.VISIBLE);
                    textViewKoneksi.setVisibility(View.VISIBLE);
                }
            }
        });

        textViewRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cekKoneksi()){
                    textViewRefresh.setVisibility(View.GONE);
                    textViewKoneksi.setVisibility(View.GONE);
                    setPertanyaan(nomor);
                }else {
                    textViewRefresh.setVisibility(View.VISIBLE);
                    textViewKoneksi.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setPertanyaan(final int nomor){
        if (nomor<=10){

            DatabaseReference myRef = database.getReference("Test").child(String.valueOf(umur)).child(String.valueOf(nomor));
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (mProgressBar != null) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    Test test = dataSnapshot.getValue(Test.class);

                    if (test.imageUrl!=null){
                        Glide
                                .with(TestActivity.this)
                                .load(test.getImageUrl())
                                .into(gmbr);
                    }

                    noPertanyaan.setText(String.valueOf(nomor)+" /10");
                    pertanyaan.setText(test.pertanyaan.replace("_b","\n"));

                    kategori = test.kategori;
                    switch (kategori){
                        case 0:
                            textViewKategori.setText("Alat dan Bahan");
                            break;
                        case 1:
                            jKasar++;
                            textViewKategori.setText("Gerak Kasar");
                            cardView.setCardBackgroundColor(getResources().getColor(R.color.green));
                            break;
                        case 2:
                            jHalus++;
                            textViewKategori.setText("Gerak Halus");
                            cardView.setCardBackgroundColor(getResources().getColor(R.color.blue));
                            break;
                        case 3:
                            jBicara++;
                            textViewKategori.setText("Bicara dan Bahasa");
                            cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                            break;
                        case 4:
                            jSosialisasi++;
                            textViewKategori.setText("Sosialisasi dan Kemandirian");
                            cardView.setCardBackgroundColor(getResources().getColor(R.color.yellow));
                            break;
                    }

                    if (nomor == 0){
                        btnIya.setVisibility(View.GONE);
                        btnTidak.setVisibility(View.VISIBLE);
                        btnTidak.setText("Mulai");
                    }else {
                        mProgressBar.setVisibility(View.GONE);
                        btnIya.setVisibility(View.VISIBLE);
                        btnTidak.setVisibility(View.VISIBLE);
                        btnTidak.setText("Tidak");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    pertanyaan.setText("Failed");
                }


            });
        }else {
            Intent intent = new Intent(TestActivity.this, PenilaianActivity.class);
            intent.putExtra("NILAI",nilai);
            intent.putExtra("NK",nKasar);
            intent.putExtra("NH",nHalus);
            intent.putExtra("NB",nBicara);
            intent.putExtra("NS",nSosialisasi);

            intent.putExtra("JK",jKasar);
            intent.putExtra("JH",jHalus);
            intent.putExtra("JB",jBicara);
            intent.putExtra("JS",jSosialisasi);

            intent.putExtra("UMUR",umur);

            intent.putExtra("RP", rPertanyaan);
            intent.putExtra("RJ", rJawaban);
            startActivity(intent);
            finish();
        }
    }

    private void reset(){
        mProgressBar.setVisibility(View.VISIBLE);
        btnIya.setVisibility(View.INVISIBLE);
        btnTidak.setVisibility(View.INVISIBLE);
    }

    public boolean cekKoneksi(){
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
    }
}
