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

import com.alespero.expandablecardview.ExpandableCardView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stimulasi);

        //Define recycleview
        recycler_view = (RecyclerView) findViewById(R.id.recycler_Expand);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        umur = intent.getIntExtra("UMUR",0);

        FirebaseApp.initializeApp(this);

        //Initialize your Firebase app
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Reference to your entire Firebase database
        final DatabaseReference parentReference = database.getReference("Stimulasi").child(String.valueOf(umur));

        //reading data from firebase
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
        public void onBindChildViewHolder(StimulasiChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

            final StimulasiChild childItem = ((StimulasiParent) group).getItems().get(childIndex);
            holder.onBind(childItem.getTahapan(), childItem.getStimulasi());
            final String TitleChild=group.getTitle();
            holder.listChildStimulasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(), TitleChild, Toast.LENGTH_SHORT);
                    toast.show();
                }

            });

        }

        @Override
        public void onBindGroupViewHolder(StimulasiParentViewHolder holder, int flatPosition, final ExpandableGroup group) {
            holder.setParentTitle(group);

            if(group.getItems()==null)
            {
                holder.listGroup.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
                    @Override
                    public void onExpandChanged(View v, boolean isExpanded) {
                        Toast.makeText(getApplicationContext(), isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }


    }
}
