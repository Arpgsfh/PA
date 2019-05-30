package org.d3ifcool.denver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class StimulasiActivity extends AppCompatActivity {
    private RecyclerView recycler_view;

    int umur;
    int tampilGK, tampilGH, tampilSK, tampilBB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stimulasi);

        //Define recycleview
        recycler_view = (RecyclerView) findViewById(R.id.recycler_Expand);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        umur = intent.getIntExtra("UMUR",0);
        tampilGK = intent.getIntExtra("GK",0);
        tampilGH = intent.getIntExtra("GH",0);
        tampilBB = intent.getIntExtra("BB",0);
        tampilSK = intent.getIntExtra("SK",0);

        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference parentReference = database.getReference("Stimulasi").child(String.valueOf(umur));
        parentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<StimulasiParent> Parent = new ArrayList<>();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()){
                    final String ParentKey = snapshot.getKey().toString();
                    DatabaseReference childReference = parentReference.child(ParentKey);
                    childReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final List<StimulasiChild> Child = new ArrayList<>();

                            for (DataSnapshot snapshot1:dataSnapshot.getChildren())
                            {
                                StimulasiChild ChildValue =  snapshot1.getValue(StimulasiChild.class);
                                Child.add(ChildValue);
                            }
                            Parent.add(new StimulasiParent(ParentKey, Child));
                            DocExpandableRecyclerAdapter adapter = new DocExpandableRecyclerAdapter(Parent);
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

    public class DocExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<StimulasiParentViewHolder, StimulasiChildViewHolder> {

        public DocExpandableRecyclerAdapter(List<StimulasiParent> groups) {
            super(groups);
        }

        @Override
        public StimulasiParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_stimulasi, parent, false);
            return new StimulasiParentViewHolder(view);
        }

        @Override
        public StimulasiChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_stimulasi, parent, false);
            return new StimulasiChildViewHolder(view);

        }

        @Override
        public void onBindChildViewHolder(final StimulasiChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
            final StimulasiChild childItem = ((StimulasiParent) group).getItems().get(childIndex);
            holder.onBind(childItem.getTahapan(), childItem.getStimulasi());
            final String title = group.getTitle();
            switch (title){
                case "1":
                    holder.layoutChildStimulasi.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case "2":
                    holder.layoutChildStimulasi.setBackgroundColor(getResources().getColor(R.color.blue));
                    break;
                case "3":
                    holder.layoutChildStimulasi.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
                case "4":
                    holder.layoutChildStimulasi.setBackgroundColor(getResources().getColor(R.color.yellow));
                    break;
            }
        }

        @Override
        public void onBindGroupViewHolder(final StimulasiParentViewHolder holder, int flatPosition, final ExpandableGroup group) {
            holder.setParentTitle(group);

            if (tampilGK==1 && flatPosition==0){
                holder.layout.setVisibility(View.GONE);
            }else if(tampilGH==1 && flatPosition==1){
                holder.layout.setVisibility(View.GONE);
            }else if (tampilBB==1 && flatPosition==2){
                holder.layout.setVisibility(View.GONE);
            }else if (tampilSK==1 && flatPosition==3){
                holder.layout.setVisibility(View.GONE);
            }

            if(group.getItems()==null) {
            }
        }
    }
}
