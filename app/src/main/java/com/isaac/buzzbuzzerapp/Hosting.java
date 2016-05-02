package com.isaac.buzzbuzzerapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import org.w3c.dom.Text;


public class Hosting extends AppCompatActivity {
    /////////////////////////////////
    // Declare location variables //
    ///////////////////////////////
    protected static final String TAG = "Host Activity ";
    protected static final int GPS_MIN_DIST_IN_METERS = 5;
    protected static final int GPS_MIN_TIME_IN_MILLISEC = 0;
    LocationManager locationManager;
    Location mLastLocation;
    String mLatitudeText;
    String mLongitudeText;
    String provider = locationManager.GPS_PROVIDER;
    LocationListener locationListener;
    boolean locationAvailable = false;
    ///////////////////////////////
    // Declare server variables //
    /////////////////////////////
    String partyName_toServer = null;
    boolean isPrivate_toServer = true;
    double latitude_toServer;
    double longitude_toServer;
    protected static final int GPS_FAIL_LONG_LAT = 360;
    ///////////////////////////////
    // Declare layout variables //
    /////////////////////////////
    TextView latitudeTextV;
    TextView longitudeTextV;
    Button hostButton;
    RadioButton radioButtonPrivate,radioButtonPublic;
    EditText partyNameTextEdit;
    TextView testDisplay;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting);

        //Layout stuff
        longitudeTextV = (TextView) findViewById(R.id.longitudeTextV);
        latitudeTextV = (TextView) findViewById(R.id.latitudeTextV);
        hostButton = (Button) findViewById(R.id.hostButton);
        radioButtonPrivate = (RadioButton) findViewById(R.id.RB_private);
        radioButtonPublic = (RadioButton) findViewById(R.id.RB_public);
        partyNameTextEdit = (EditText) findViewById(R.id.EditTextPartyName);
        testDisplay = (TextView) findViewById(R.id.testServerStuff);

        // Location stuff
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //mLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                if(location != null) {
                    if(location.getLatitude() == 0 || location.getLongitude() == 0) {
                        toast = Toast.makeText(Hosting.this, "searching for gps", toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        /*latitudeTextV.setText("" + location.getLatitude());
                        longitudeTextV.setText("" + location.getLongitude());*/
                        latitude_toServer = location.getLatitude();
                        longitude_toServer = location.getLongitude();
                        locationAvailable = true;
                        toast = Toast.makeText(Hosting.this, "GPS located successfully", toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else {
                    latitude_toServer = GPS_FAIL_LONG_LAT; //360
                    longitude_toServer = GPS_FAIL_LONG_LAT;//360
                    toast = Toast.makeText(Hosting.this, "No location Available", toast.LENGTH_SHORT);
                    toast.show();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //no need to implement
            }

            @Override
            public void onProviderEnabled(String provider) {
                //no need to implement
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        //locationManager.requestLocationUpdates(provider, GPS_MIN_TIME_IN_MILLISEC, GPS_MIN_DIST_IN_METERS, locationListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET}, 10);
                return;
            } else {
                configureButton();
            }
        }
    }//end onCreate()


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked(); // Is the button now checked?

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.RB_private:
                if (checked)
                    isPrivate_toServer = true;
                    break;
            case R.id.RB_public:
                if (checked)
                    isPrivate_toServer = false;
                    break;
        }
    }//end onRadioButtonClicked()

    public void configureButton() {
        hostButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Hosting.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Hosting.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(provider, GPS_MIN_TIME_IN_MILLISEC, GPS_MIN_DIST_IN_METERS, locationListener);
                /*mLastLocation = locationManager.getLastKnownLocation(provider);
                if(mLastLocation != null) {

                    latitude_toServer = mLastLocation.getLatitude();
                    longitude_toServer = mLastLocation.getLongitude();
                    latitudeTextV.setText("" + latitude_toServer);
                    longitudeTextV.setText("" + longitude_toServer);
                }
                else {
                    latitude_toServer = GPS_FAIL_LONG_LAT;
                    longitude_toServer = GPS_FAIL_LONG_LAT;
                    toast = Toast.makeText(Hosting.this, "No location Available (sucks)", toast.LENGTH_SHORT);
                    toast.show();
                }*/

                testDisplay.setText(createServerMessage());
                if(locationAvailable == true) {
                    Intent intent = new Intent(Hosting.this, Party.class);
                    startActivity(intent);
                }
                else
                {
                    toast = Toast.makeText(Hosting.this, "GPS Not ready yet", toast.LENGTH_SHORT);
                    toast.setMargin(40,40);
                    toast.show();
                }
            }// end onClick()
        });
    }//end configureButton()
    private String createServerMessage(){
        partyName_toServer = partyNameTextEdit.getText().toString();
        String boolMess;
        if(isPrivate_toServer)
            boolMess = "true";
        else
            boolMess = "false";
        return "PartyName: "+partyName_toServer+"\nLat: " +latitude_toServer +"\nLong: " + longitude_toServer +"\nPrivate?: "+ boolMess;
    }
}