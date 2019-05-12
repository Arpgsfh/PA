package org.d3ifcool.denver;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.List;

public class RiwayatActivity extends AppCompatActivity {

    private RecyclerView recycler_view;

    int umur;

    public static final String PROFILE = "profile";
    String idProfil;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        id = acct.getId();

        SharedPreferences prefs = getSharedPreferences(PROFILE, MODE_PRIVATE);
        idProfil = prefs.getString("ID", null);


        //Define recycleview
        recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);

        //Initialize your Firebase app
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Reference to your entire Firebase database
        final DatabaseReference parentReference = database.getReference("Riwayat").child(id).child(idProfil);

        //reading data from firebase
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
        public void onBindChildViewHolder(final RiwayatChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
            final RiwayatChild childItem = ((RiwayatParent) group).getItems().get(childIndex);
            holder.onBind(childItem.getNama(), childItem.getTanggal(), childItem.getNilai(), childItem.getnKasar(), childItem.getnHalus(), childItem.nBicara, childItem.nSosialisasi, childItem.getjKasar(), childItem.getjHalus(), childItem.getjBicara(), childItem.jSosialisasi);

        }

        @Override
        public void onBindGroupViewHolder(final RiwayatParentViewHolder holder, int flatPosition, final ExpandableGroup group) {
            holder.setParentTitle(group);

            if(group.getItems()==null) {
            }
        }
    }
}