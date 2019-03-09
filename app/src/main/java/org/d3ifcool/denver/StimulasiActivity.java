package org.d3ifcool.denver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StimulasiActivity extends AppCompatActivity {

    DatabaseReference databaseStimulasi;

    StimulasiAdapter mAdapter;
    RecyclerView recyclerView;
    List<Stimulasi> stimulasis;

    int umur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stimulasi);

        Intent intent = getIntent();
        umur = intent.getIntExtra("UMUR",0);

        databaseStimulasi = FirebaseDatabase.getInstance().getReference("Stimulasi").child(String.valueOf(umur));

        mAdapter = new StimulasiAdapter(this, stimulasis);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewStimulasi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        stimulasis = new ArrayList<>();

        databaseStimulasi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                stimulasis.clear();

                for (DataSnapshot stimulasiSnapshot : dataSnapshot.getChildren()){
                    Stimulasi stimulasi = stimulasiSnapshot.getValue(Stimulasi.class);
                    stimulasis.add(stimulasi);
                }

                if (this != null){
                    StimulasiAdapter stimulasiAdapter = new StimulasiAdapter(StimulasiActivity.this, stimulasis);
                    recyclerView.setAdapter(stimulasiAdapter);
                }

                StimulasiAdapter stimulasiAdapter = new StimulasiAdapter(StimulasiActivity.this, stimulasis);
                recyclerView.setAdapter(stimulasiAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
