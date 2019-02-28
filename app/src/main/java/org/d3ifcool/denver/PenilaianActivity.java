package org.d3ifcool.denver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PenilaianActivity extends AppCompatActivity {

    TextView penilaian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penilaian);

        final Intent intent = getIntent();
        int nilai = intent.getIntExtra("NILAI",0);
        penilaian = (TextView) findViewById(R.id.penilaian);

        Button button = (Button) findViewById(R.id.button);

        if (nilai<8){
            penilaian.setText("Penyimpangan");
        }else {
            penilaian.setText("Sesuai");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PenilaianActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }
}
