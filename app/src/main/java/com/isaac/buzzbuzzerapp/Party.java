package com.isaac.buzzbuzzerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Party extends AppCompatActivity {

    private TextView partyIdText;
    private TextView countDown;
    private Button voteButton;
    private ListView guestList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> guests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        partyIdText = (TextView) findViewById(R.id.partyId);
        countDown = (TextView) findViewById(R.id.countDown);
        voteButton = (Button) findViewById(R.id.voteButton);
        guestList = (ListView) findViewById(R.id.guestList);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, guests);
        guestList.setAdapter(adapter);
        adapter.setNotifyOnChange(true);


    }

    public void onPressVote(View view){

    }
}
