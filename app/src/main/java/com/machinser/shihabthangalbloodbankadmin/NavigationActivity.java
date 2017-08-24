package com.machinser.shihabthangalbloodbankadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NavigationActivity extends AppCompatActivity {
    Button categrory,add,view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        categrory = (Button)findViewById(R.id.cagtegory);
        add = (Button)findViewById(R.id.adddonor);
        view = (Button)findViewById(R.id.deletedonors);

        categrory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigationActivity.this,AddCategory.class));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigationActivity.this,MainActivity.class));
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigationActivity.this,ViewAllDetails.class));
            }
        });
    }
}
