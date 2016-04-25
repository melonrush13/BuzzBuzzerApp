package com.isaac.buzzbuzzerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class signUp extends AppCompatActivity {

    EditText name;
    EditText feet;
    EditText inches;
    EditText weight;
    Button createAccount;
    CheckBox male;
    CheckBox female;
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = (EditText) findViewById(R.id.name);
        feet = (EditText) findViewById(R.id.heightFt);
        inches = (EditText) findViewById(R.id.heightIn);
        weight = (EditText) findViewById(R.id.weight);
        createAccount = (Button) findViewById(R.id.createAccount);
        createAccount.setEnabled(false);
        male = (CheckBox) findViewById(R.id.male);
        female = (CheckBox) findViewById(R.id.female);
        preference = getPreferences(Context.MODE_PRIVATE);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        feet.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        inches.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enableSubmitIfReady();
                female.setChecked(false);
                male.setChecked(true);
            }
        }
        );

        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enableSubmitIfReady();
                male.setChecked(false);
                female.setChecked(true);
            }
        }
        );
    }

    public boolean enableSubmitIfReady(){
        String name = this.name.getText().toString();
        String in = this.inches.getText().toString();
        String ft = this.feet.getText().toString();
        String weight = this.weight.getText().toString();
        boolean male = this.male.isChecked();
        boolean female = this.female.isChecked();

        if (!name.equals("") && !in.equals("")
                && !ft.equals("") && !weight.equals("")
                && ((male && !female) || (!male && female))) {
            createAccount.setEnabled(true);
            return true;
        }
        createAccount.setEnabled(false);
        return false;
    }

    public void onPressCreateAccount(View view){
        String name = this.name.getText().toString();
        String in = this.inches.getText().toString();
        String ft = this.feet.getText().toString();
        String weight = this.weight.getText().toString();
        boolean male = this.male.isChecked();
        boolean female = this.female.isChecked();
        preference.edit().putBoolean("male", male);
        preference.edit().putBoolean("female", female);
        preference.edit().putInt("feet", Integer.parseInt(ft));
        preference.edit().putInt("inches", Integer.parseInt(in));
        preference.edit().putInt("weight", Integer.parseInt(weight));
        preference.edit().putString("name", name);
        preference.edit().putBoolean("hasAccount", true);
        preference.edit().apply();

        Intent intent = new Intent(this, LandingPage.class);
        startActivity(intent);
    }
}
