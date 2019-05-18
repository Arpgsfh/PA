package org.d3ifcool.denver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    DatabaseReference databasePertanyaan;
    ReportAdapter mAdapter;
    RecyclerView recyclerView;
    List<Report> reportList;

    public static final String PROFILE = "profile";
    String idProfil;

    String id;

    String childIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        id = acct.getId();

        SharedPreferences prefs = getSharedPreferences(PROFILE, MODE_PRIVATE);
        idProfil = prefs.getString("ID", null);

        Intent intent = getIntent();
        childIndex = intent.getStringExtra("CHILD");

        databasePertanyaan = FirebaseDatabase.getInstance().getReference("Detail").child(id).child(idProfil).child(childIndex);


        mAdapter = new ReportAdapter(this, reportList);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerReport);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reportList = new ArrayList<>();

        databasePertanyaan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                reportList.clear();

                for (DataSnapshot reportSnapshot : dataSnapshot.getChildren()){
                    Report report = reportSnapshot.getValue(Report.class);
                    reportList.add(report);
                }

                if (this != null){
                    ReportAdapter reportAdapter = new ReportAdapter(ReportActivity.this, reportList);
                    recyclerView.setAdapter(reportAdapter);
                }

                ReportAdapter reportAdapter = new ReportAdapter(ReportActivity.this, reportList);
                recyclerView.setAdapter(reportAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



    }
}
