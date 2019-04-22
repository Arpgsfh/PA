package org.d3ifcool.denver;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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

public class RiwayatActivity extends AppCompatActivity {

    public static final String PROFILE = "profile";
    String idProfil;

    DatabaseReference databaseRiwayat;

    RiwayatAdapter mAdapter;
    RecyclerView recyclerView;
    List<Riwayat> riwayats;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        id = acct.getId();

        SharedPreferences prefs = getSharedPreferences(PROFILE, MODE_PRIVATE);
        idProfil = prefs.getString("ID", null);

        databaseRiwayat = FirebaseDatabase.getInstance().getReference("Riwayat").child(id).child(idProfil);

        mAdapter = new RiwayatAdapter(this, riwayats);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        riwayats = new ArrayList<>();

        databaseRiwayat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                riwayats.clear();

                for (DataSnapshot riwayatSnapshot : dataSnapshot.getChildren()){
                    Riwayat riwayat = riwayatSnapshot.getValue(Riwayat.class);
                    riwayats.add(riwayat);
                }

                if (this != null){
                    RiwayatAdapter riwayatAdapter = new RiwayatAdapter(RiwayatActivity.this, riwayats);
                    recyclerView.setAdapter(riwayatAdapter);
                }

                RiwayatAdapter riwayatAdapter = new RiwayatAdapter(RiwayatActivity.this, riwayats);
                recyclerView.setAdapter(riwayatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}