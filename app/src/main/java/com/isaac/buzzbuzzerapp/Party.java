package com.isaac.buzzbuzzerapp;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        //guestList.setAdapter(adapter);
        //adapter.setNotifyOnChange(true);

        RequestTask requestTask = new RequestTask();

        new RequestTask(getApplicationContext(), new RequestTask.AsyncDataFetchResponse() {
            @Override
            public void processFinish(String output) {
                //output should be the JSON string fetched from the endpoint.

            }
        }).execute("http://localhost:8080/guestList?PartyID=test");

    }

    public void onPressVote(View view){

    }
}
