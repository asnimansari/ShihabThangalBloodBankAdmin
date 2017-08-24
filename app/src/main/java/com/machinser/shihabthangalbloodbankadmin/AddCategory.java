package com.machinser.shihabthangalbloodbankadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class AddCategory extends AppCompatActivity {


    ListView lv;
    EditText ed;
    Button bd;
    DatabaseReference databaseReference;
    ArrayList<String> regions;
    ArrayAdapter<String> regions_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        bd = (Button)findViewById(R.id.saveRegion);
        lv = (ListView)findViewById(R.id.lv);
        ed = (EditText)findViewById(R.id.newregion);

        databaseReference = CusUtils.getDatabase().getReference().child("regions");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                regions = new ArrayList<String>();
                Region region = new Region();
                regions.add("None");

                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for(DataSnapshot dataSnapshot1:dataSnapshots){
                    region = dataSnapshot1.getValue(Region.class);
                    regions.add(region.region_name);

                }
                regions_adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.individual_text,regions);
                lv.setAdapter(regions_adapter);

                regions_adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Region region = new Region();
                region.date = new Date().toString();
                region.region_name = ed.getText().toString();
                if (region.region_name.compareToIgnoreCase("")==0) return;
                else if (region.region_name == null) return;
                else {
                    databaseReference.push().setValue(region);
                    Toast.makeText(AddCategory.this, "Added New Region", Toast.LENGTH_SHORT).show();
                    ed.setText("");
                }

            }
        });
    }
}
