package org.d3ifcool.denver;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TentangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        final TextView pendahuluan = (TextView) findViewById(R.id.pendahuluan);
        final TextView alatInstrumen = (TextView) findViewById(R.id.alatInstrumen);
        final TextView caraMenggunakan = (TextView) findViewById(R.id.caraMenggunakan);
        final TextView interpretasi = (TextView) findViewById(R.id.interpretasi);
        final TextView intervensi = (TextView) findViewById(R.id.intervensi);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Tentang");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Tentang tentang = dataSnapshot.getValue(Tentang.class);

                pendahuluan.setText(tentang.pendahuluan.replace("_b","\n"));
                alatInstrumen.setText(tentang.alatInstrumen.replace("_b","\n"));
                caraMenggunakan.setText(tentang.caraMenggunakan.replace("_b","\n"));
                interpretasi.setText(tentang.interpretasi.replace("_b","\n"));
                intervensi.setText(tentang.intervensi.replace("_b","\n"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pendahuluan.setText("Failed");
            }
        });


    }
}
