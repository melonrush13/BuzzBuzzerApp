package com.isaac.buzzbuzzerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Warning extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        preferences = getPreferences(Context.MODE_PRIVATE);
        if(preferences.getBoolean("acceptedTerms", false)){
            if(preferences.getBoolean("hasAccount", false)){
                Intent intent = new Intent(this, LandingPage.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, signUp.class);
                startActivity(intent);
            }
        }
    }

    public void onPress(View view){
        preferences.edit().putBoolean("acceptedTerms", true);
        preferences.edit().apply();
        Intent intent = new Intent(this, signUp.class);
        startActivity(intent);
    }

        //Intent intent = new Intent(this, ViewEventActivity.class);
        //startActivity(intent);

}
