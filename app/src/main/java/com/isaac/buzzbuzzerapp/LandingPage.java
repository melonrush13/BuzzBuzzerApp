package com.isaac.buzzbuzzerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
    }

    public void onPressHost(View view){
        Intent intent = new Intent(this, Hosting.class);
        startActivity(intent);
    }

    public void onPressJoin(View view){
        Intent intent = new Intent(this, Joining.class);
        startActivity(intent);
    }
}
