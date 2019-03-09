package org.d3ifcool.denver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TestActivity extends AppCompatActivity {

    TextView noPertanyaan;
    TextView pertanyaan;
    RadioGroup radioGroup;
    Button btnLanjut;

    FirebaseDatabase database;

    int umur;
    int nomor=1;
    int nilai=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        umur = intent.getIntExtra("UMUR",0);

        setPertanyaan(nomor);

        noPertanyaan = (TextView) findViewById(R.id.noPertanyaan);
        pertanyaan = (TextView) findViewById(R.id.pertanyaan);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnLanjut = (Button) findViewById(R.id.button);

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomor++;
                reset();
                setPertanyaan(nomor);

            }
        });
    }

    private void reset(){
        radioGroup.clearCheck();
        btnLanjut.setVisibility(View.INVISIBLE);
    }

    private void setPertanyaan(final int nomor){
        if (nomor<=10){
            DatabaseReference myRef = database.getReference("Test").child(String.valueOf(umur)).child(String.valueOf(nomor));

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    noPertanyaan.setText(String.valueOf(nomor));
                    pertanyaan.setText(value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    pertanyaan.setText("Failed");
                }
            });
        }else {
            Intent intent = new Intent(TestActivity.this, PenilaianActivity.class);
            intent.putExtra("NILAI",nilai);
            intent.putExtra("UMUR",umur);
            startActivity(intent);
            finish();
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        btnLanjut.setVisibility(View.VISIBLE);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioBtnYa:
                if (checked){
                    nilai++;
                }
                break;
            case R.id.radioBtnTidak:
                if (checked){
                }
                break;
        }
    }
}
