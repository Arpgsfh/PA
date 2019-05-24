package org.d3ifcool.denver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RiwayatActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String PROFILE = "profile";
    String idProfil;
    String namaProfile;
    int tahun, bulan, hari;
    int years = 0;
    int months = 0;
    int days = 0;

    private RecyclerView recycler_view;

    int umur;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        id = acct.getId();

        loadProfilPreferences();

        ConstraintLayout profilAnak = (ConstraintLayout) findViewById(R.id.profilAnak);
        TextView namaAnak = (TextView) findViewById(R.id.namaAnakTextView);
        TextView umurAnak = (TextView) findViewById(R.id.umurTextView);

        //Define recycleview
        recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        namaAnak.setText(namaProfile);
        AgeCalculator();
        umurAnak.setText(months+" Bulan");

        profilAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RiwayatActivity.this, ProfilAnakActivity.class);
                startActivity(intent1);
            }
        });

        loadDatabase();

    }

    public class DocExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<RiwayatParentViewHolder, RiwayatChildViewHolder> {

        public DocExpandableRecyclerAdapter(List<RiwayatParent> groups) {
            super(groups);
        }

        @Override
        public RiwayatParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_riwayat, parent, false);
            return new RiwayatParentViewHolder(view);
        }

        @Override
        public RiwayatChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_riwayat, parent, false);
            return new RiwayatChildViewHolder(view);
        }

        @Override
        public void onBindChildViewHolder(final RiwayatChildViewHolder holder, int flatPosition, final ExpandableGroup group, final int childIndex) {
            final RiwayatChild childItem = ((RiwayatParent) group).getItems().get(childIndex);
            holder.onBind(childItem.getNama(), childItem.getTanggal(), childItem.getNilai(), childItem.getnKasar(), childItem.getnHalus(), childItem.nBicara, childItem.nSosialisasi, childItem.getjKasar(), childItem.getjHalus(), childItem.getjBicara(), childItem.jSosialisasi);

            holder.listChildCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RiwayatActivity.this, ReportActivity.class);
                    intent.putExtra("CHILD", childItem.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onBindGroupViewHolder(final RiwayatParentViewHolder holder, int flatPosition, final ExpandableGroup group) {
            holder.setParentTitle(group);

            if(group.getItems()==null) {
            }
        }
    }

    public void loadDatabase(){
        FirebaseApp.initializeApp(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference parentReference = database.getReference("Riwayat").child(id).child(idProfil);
        parentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<RiwayatParent> Parent = new ArrayList<>();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    final String ParentKey = snapshot.getKey().toString();

                    DatabaseReference childReference = parentReference.child(ParentKey);
                    childReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final List<RiwayatChild> Child = new ArrayList<>();
                            for (DataSnapshot snapshot1:dataSnapshot.getChildren())
                            {
                                RiwayatChild ChildValue =  snapshot1.getValue(RiwayatChild.class);
                                Child.add(ChildValue);

                            }

                            Parent.add(new RiwayatParent(ParentKey, Child));

                            RiwayatActivity.DocExpandableRecyclerAdapter adapter = new RiwayatActivity.DocExpandableRecyclerAdapter(Parent);

                            recycler_view.setAdapter(adapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            System.out.println("Failed to read value." + error.toException());
                        }

                    });}}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void AgeCalculator(){
        Calendar now = Calendar.getInstance();
        years = now.get(Calendar.YEAR) - tahun;

        months = now.get(Calendar.MONTH) - bulan;

        if (months<0){
            years--;
            months = 12 + months;
        }
        months = (years*12) + months;

        days = now.get(Calendar.DAY_OF_MONTH) - hari;

        if (days<=0){
            months++;
        }
    }

    public void loadProfilPreferences(){
        SharedPreferences preferences = getSharedPreferences(PROFILE, MODE_PRIVATE);
        TextView namaAnak = (TextView) findViewById(R.id.namaAnakTextView);
        TextView umurAnak = (TextView) findViewById(R.id.umurTextView);

        idProfil = preferences.getString("ID", null);
        namaProfile = preferences.getString("NAMA", null);
        tahun = preferences.getInt("TAHUN", 0);
        bulan = preferences.getInt("BULAN", 0);
        hari = preferences.getInt("HARI", 0);

        namaAnak.setText(namaProfile);
        AgeCalculator();
        umurAnak.setText(months+" Bulan");

        preferences.registerOnSharedPreferenceChangeListener(RiwayatActivity.this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        loadProfilPreferences();
        recycler_view.setAdapter(null);
        loadDatabase();
    }
}