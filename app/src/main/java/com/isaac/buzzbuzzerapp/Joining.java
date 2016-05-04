package com.isaac.buzzbuzzerapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter
import android.widget.EditText;
import android.widget.ListView;

import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.TextView;
import android.content.DialogInterface
import android.content.Intent;


public class Joining extends AppCompatActivity {

    //data you want to display in list (list of parties)
    private String[] sampleParty = {"Party 1", "Party 2", "Party 3", "Party 4", "Party 5",
            "Party 6", "Party 7", "Party 8", "Party 9", "Party 10", "Party 11", "Party 12",
            "Party 13", "So many fucking partieszzz" };
            
    EditText currentText;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joining);

        ListView partyListView;
        partyListView = (ListView) findViewById(R.id.partyList);
        currentText = (EditText) findViewById(R.id.insertPartyNumber);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sampleParty);
        partyListView.setAdapter(arrayAdapter);

        partyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = ((TextView)view).getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(Joining.this);
                builder.setTitle("Are you sure you would like to join "
                        + item + "?");
                builder.setMessage("Select an option:");
                builder.setCancelable(true);
                builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Joining Party...", Toast.LENGTH_SHORT)
                                .show();
                                
                        Intent intent = new Intent(Joining.this, Party.class);
                        startActivity(intent);

                        //join party
                        /*
                        //TODO: Connect with server to join the party
                        new RequestTask(getApplicationContext(), new RequestTask.AsyncDataFetchResponse() {
                            @Override
                            public void processFinish(String output) {
                                //output should be the JSON string fetched from the endpoint.

                            }
                        }).execute("http://localhost:8080/joinParty?PartyID=test");
                        */
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
    
    public void onJoinPartyClick(View view){
        if(currentText.getText().toString().length() > 0) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Are you sure you would like to join?");
            builder1.setMessage("Select an option:");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(),
                                    "Joining Party...", Toast.LENGTH_SHORT)
                                    .show();

                            //join party
                        /*
                        //TODO: Connect with server to join the party
                        new RequestTask(getApplicationContext(), new RequestTask.AsyncDataFetchResponse() {
                            @Override
                            public void processFinish(String output) {
                                //output should be the JSON string fetched from the endpoint.

                            }
                        }).execute("http://localhost:8080/joinParty?PartyID=test");
                        */


                            //TODO: Change this to Party page once party page works
                            Intent intent = new Intent(Joining.this, Party.class);
                            startActivity(intent);
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder1.show();
        }
        if (currentText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter a Party Code!", Toast.LENGTH_SHORT)
                    .show();
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "Please Enter a Party Code!", Toast.LENGTH_SHORT)
                    .show();

        }

    }


}

