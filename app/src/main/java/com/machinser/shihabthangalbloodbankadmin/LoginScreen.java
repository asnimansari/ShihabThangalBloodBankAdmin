package com.machinser.shihabthangalbloodbankadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {

    EditText username,password;
    Button login;

    boolean has_updated_database = false;

    DatabaseReference databaseReference;

    ArrayList<Users> usersArrayList;
    String user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);


        databaseReference = CusUtils.getDatabase().getReference().child("admin_users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersArrayList = new ArrayList<>();
                Users users = new Users();

                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshot1:dataSnapshots){
                    users = dataSnapshot1.getValue(Users.class);
                    usersArrayList.add(users);

                }

                Toast.makeText(LoginScreen.this, "FETCHED USER INFORMATION", Toast.LENGTH_SHORT).show();
                has_updated_database = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean has_authenticated = false;


                user = username.getText().toString().trim();
                pass = password.getText().toString().trim();

                Users userclass  = new Users();

                if  (user == null || pass == null || user.compareToIgnoreCase("") == 0 || pass.compareToIgnoreCase("") == 0){
                    Toast.makeText(LoginScreen.this, "Please Enter User Name and Password", Toast.LENGTH_SHORT).show();
                }
                else if(has_updated_database){
                    int size = usersArrayList.size();
                    for (int i = 0;i<size;i++){
                        userclass = usersArrayList.get(i);

                        if (userclass.username.compareToIgnoreCase(user) == 0){
                            if (userclass.password.compareToIgnoreCase(pass) == 0){
                                has_authenticated = true;
                                break;

                            }
                        }



                    }




                }
                else{
                    Toast.makeText(LoginScreen.this, "Please Connect to Internet and Wait Till User List is fetched", Toast.LENGTH_SHORT).show();
                }

                if (has_authenticated){
                    Toast.makeText(LoginScreen.this, "Authenticated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginScreen.this,NavigationActivity.class));
                    finish();

                }else {
                    Toast.makeText(LoginScreen.this, "Invalid UserName or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
