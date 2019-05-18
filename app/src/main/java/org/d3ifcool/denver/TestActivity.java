package org.d3ifcool.denver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.squareup.picasso.Picasso;

public class TestActivity extends AppCompatActivity {

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

    String rPertanyaan[] = new String[11];
    int rJawaban[] = new int[11];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        umur = intent.getIntExtra("UMUR",0);

        setPertanyaan(nomor);

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
                if (kategori==1){
                    nKasar++;
                }else if (kategori==2){
                    nHalus++;
                }else if (kategori==3){
                    nBicara++;
                }else if (kategori==4){
                    nSosialisasi++;
                }

                if (nomor!=0){
                    rPertanyaan[nomor] = pertanyaan.getText().toString();
                    rJawaban[nomor] = 1;
                }

                reset();
                nomor++;
                nilai++;
                setPertanyaan(nomor);
            }
        });

        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nomor!=0){
                    rPertanyaan[nomor] = pertanyaan.getText().toString();
                    rJawaban[nomor] = 0;
                }

                reset();
                nomor++;
                setPertanyaan(nomor);
            }
        });
    }

    private void setPertanyaan(final int nomor){
        if (nomor<=10){
            DatabaseReference myRef = database.getReference("Test").child(String.valueOf(umur)).child(String.valueOf(nomor));

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Test test = dataSnapshot.getValue(Test.class);
                    noPertanyaan.setText(String.valueOf(nomor)+" /10");
                    pertanyaan.setText(test.pertanyaan.replace("_b","\n"));

                    kategori = test.kategori;
                    if (kategori==1){
                        jKasar++;
                        textViewKategori.setText("Gerak Kasar");
                        cardView.setCardBackgroundColor(getResources().getColor(R.color.green));
                    }else if (kategori==2){
                        jHalus++;
                        textViewKategori.setText("Gerak Halus");
                        cardView.setCardBackgroundColor(getResources().getColor(R.color.blue));
                    }else if (kategori==3){
                        jBicara++;
                        textViewKategori.setText("Bicara dan Bahasa");
                        cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
                    }else if (kategori==4){
                        jSosialisasi++;
                        textViewKategori.setText("Sosialisasi dan Kemandirian");
                        cardView.setCardBackgroundColor(getResources().getColor(R.color.yellow));
                    }else {
                        textViewKategori.setText("Alat dan Bahan");
                    }

                    Picasso.with(TestActivity.this)
                            .load(test.getImageUrl())
                            .into(gmbr);

                    if (nomor == 0){
                        btnIya.setVisibility(View.GONE);
                        btnTidak.setVisibility(View.VISIBLE);
                        btnTidak.setText("Mulai");
                    }else {
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
        btnIya.setVisibility(View.INVISIBLE);
        btnTidak.setVisibility(View.INVISIBLE);
    }
}
