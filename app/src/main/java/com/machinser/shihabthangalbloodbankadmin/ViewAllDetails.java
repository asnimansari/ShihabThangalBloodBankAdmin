package com.machinser.shihabthangalbloodbankadmin;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllDetails extends AppCompatActivity {


    DatabaseReference databaseReference;

    ListView listView;
    ArrayList<String> keyList;
    ArrayList<BloodDonor> bloodDonors;
    ArrayList<String> bloodDonorsName;

    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_details);
        listView = (ListView)findViewById(R.id.lv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewAllDetails.this);


                builder.setMessage("Are you sure you want to delete "+bloodDonorsName.get(position)+"?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                databaseReference.child(keyList.get(position)).removeValue();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        databaseReference = CusUtils.getDatabase().getReference().child("blood_donors");






        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                BloodDonor bloodDonor = new BloodDonor();


                keyList = new ArrayList<String>();
                bloodDonors = new ArrayList<BloodDonor>();
                bloodDonorsName = new ArrayList<String>();
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for(DataSnapshot dataSnapshot1:dataSnapshots){
                    Log.e("KEy",dataSnapshot1.getKey());

                    keyList.add(dataSnapshot1.getKey());
                    bloodDonor = dataSnapshot1.getValue(BloodDonor.class);
                    bloodDonors.add(bloodDonor);
                    bloodDonorsName.add(bloodDonor.full_name);
                }
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.individual_text,bloodDonorsName);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
