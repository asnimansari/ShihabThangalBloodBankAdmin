package com.machinser.shihabthangalbloodbankadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.satsuware.usefulviews.LabelledSpinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , LabelledSpinner.OnItemChosenListener{
    LabelledSpinner bloodgroup;
   LabelledSpinner region_spinner;
    private String[] bloodgroups,regions;
    private DatabaseReference databaseReference;
    private Button savel;
    private EditText full_name, age,address1_ed,address2_ed,phone_no;
    private String blood_group_text;
    String region_text;

    boolean is_blood_group_ok = false;
    boolean is_region_ok = false;

//    TextInputLayout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bloodgroup = (LabelledSpinner) findViewById(R.id.blood_group_spinner);
        region_spinner = (LabelledSpinner) findViewById(R.id.region);
        full_name = (EditText) findViewById(R.id.full_name);
        age = (EditText) findViewById(R.id.age);
        savel = (Button) findViewById(R.id.save);
        address1_ed = (EditText) findViewById(R.id.address1);
        address2_ed = (EditText) findViewById(R.id.address2);
        phone_no = (EditText) findViewById(R.id.phone_number);
        savel.setOnClickListener(this);
        bloodgroups = new String[]{"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        regions = new String[]{"North Kerala", "Middle Kerala", "South Kerala"};
        ArrayAdapter<String> blood_group_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodgroups);
        ArrayAdapter<String> regions_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regions);
//        bloodgroup.setAdapter(blood_group_adapter);
        bloodgroup.setItemsArray(R.array.blood_groups_array);
        region_spinner.setItemsArray(R.array.region_array);
        bloodgroup.setOnItemChosenListener(this);
        region_spinner.setOnItemChosenListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }

    private void saveDonorInformation() {
        String age_string = age.getText().toString();
        String full_name_string = full_name.getText().toString();
//        String blood_group_text = bloodgroup.getSelectedItem().toString();
        String address_1 = address1_ed.getText().toString();
        String address_2 = address2_ed.getText().toString();
        String phone_no_string = phone_no.getText().toString();
        if (!is_blood_group_ok){
            Toast.makeText(this, "SELECT A BLOOD GROUP", Toast.LENGTH_SHORT).show();


        }
        if (!is_region_ok){
            Toast.makeText(this, "SELECT A REGION", Toast.LENGTH_SHORT).show();


        }
        if (full_name_string.length()==0){
            Toast.makeText(this, "ENTER NAME", Toast.LENGTH_SHORT).show();


        }
        if (!android.util.Patterns.PHONE.matcher(phone_no_string).matches()){
            Toast.makeText(this, "ENTER VALID PHONE NUMBER", Toast.LENGTH_SHORT).show();



        }



        if(is_blood_group_ok && is_region_ok && full_name_string.length()!=0 && android.util.Patterns.PHONE.matcher(phone_no_string).matches()){

            BloodDonor bloodDonor = new BloodDonor(region_text,blood_group_text,full_name_string, age_string,address_1,address_2,phone_no_string);
            databaseReference.push().setValue(bloodDonor);
            Toast.makeText(this, "SAVED NEW DONOR... KEEP GOING", Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }





    }

    @Override
    public void onClick(View v) {

        saveDonorInformation();

    }
    @Override
    public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
        Log.e("POS",position+"");
        switch (labelledSpinner.getId()) {
            case R.id.blood_group_spinner:
                String[] blood_array =  getResources().getStringArray(R.array.blood_groups_array);
                blood_group_text = blood_array[position];
                if(position == 0){
                    is_blood_group_ok = false;
                }else{
                    is_blood_group_ok = true;
                }


                // Do something here
                break;
            case R.id.region:
                String[] region_array =  getResources().getStringArray(R.array.region_array);
                region_text = region_array[position];
                if(position == 0){
                    is_region_ok = false;
                }else{
                    is_region_ok = true;
                }

                // Do something here
                break;

        }
    }

    @Override
    public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

    }
}