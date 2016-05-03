package com.isaac.buzzbuzzerapp;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Alex K on 5/1/2016.
 */

public class Hosting extends AppCompatActivity implements OnMapReadyCallback {
    /////////////////////////////////
    // Declare location variables //
    ///////////////////////////////
    protected static final int SECONDS_IN_mS = 1000;
    protected static final int GPS_MIN_DIST_IN_METERS = 20;
    protected static final int GPS_MIN_TIME_IN_MILLISEC = 10*SECONDS_IN_mS;
    LocationManager locationManager;
    String provider = locationManager.GPS_PROVIDER;
    LocationListener locationListener;
    boolean locationAvailable = false;
    ///////////////////////////////
    // Declare server variables //
    /////////////////////////////
    protected static final int GPS_FAIL_LONG_LAT = 360;
    String partyName_toServer = null;
    boolean isPrivate_toServer = true;
    double latitude_toServer;
    double longitude_toServer;

    ///////////////////////////////
    // Declare layout variables //
    /////////////////////////////
    EditText partyNameTextEdit;
    Button hostButton;
    RadioButton radioButtonPrivate, radioButtonPublic;
    TextView testDisplay;
    Toast toast;

    ////////////////
    // Map Stuff //
    //////////////
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting);

        // Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(Hosting.this);

        //Layout stuff
        hostButton = (Button) findViewById(R.id.hostButton);
        radioButtonPrivate = (RadioButton) findViewById(R.id.RB_private);
        radioButtonPublic = (RadioButton) findViewById(R.id.RB_public);
        partyNameTextEdit = (EditText) findViewById(R.id.EditTextPartyName);
        testDisplay = (TextView) findViewById(R.id.testServerStuff);

        // Location stuff
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {//
                    if (location.getLatitude() <= 1 && location.getLatitude()>= -1 || location.getLongitude() <= 1 && location.getLongitude() >= -1) {
                        toast = Toast.makeText(Hosting.this, "searching for gps, please hold tight", toast.LENGTH_SHORT);
                        toast.show();
                    } else {//
                        latitude_toServer = location.getLatitude();
                        longitude_toServer = location.getLongitude();
                        locationAvailable = true;
                        toast = Toast.makeText(Hosting.this, "GPS located successfully", toast.LENGTH_LONG);
                        toast.show();
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude_toServer,longitude_toServer)).title("tits") );
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude_toServer,longitude_toServer),17.0f));

                    }
                } else { //location is invalid
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
                toast = Toast.makeText(Hosting.this, "Please turn on your GPS", toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
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
        switch (view.getId()) {
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
                    // TODO: May need to implement security check if more problems with GPS occur
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(provider, GPS_MIN_TIME_IN_MILLISEC, GPS_MIN_DIST_IN_METERS, locationListener);

                testDisplay.setText(createServerMessage());
                if (locationAvailable == true && partyNameTextEdit.getText().toString().length() > 0) {
                    Intent intent = new Intent(Hosting.this, Party.class);
                    toast = Toast.makeText(Hosting.this, "New party successfully made!", toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 5);
                    toast.show();
                    //TODO: Add code here to send information to server
                    /*
                    * make a function sendPartyInfoToServer()
                    */
                    startActivity(intent);
                } else if (partyNameTextEdit.getText().toString().length() == 0) {
                    toast = Toast.makeText(Hosting.this, "You forgot to enter a party name!", toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -50);
                    toast.show();
                } else {
                    toast = Toast.makeText(Hosting.this, "GPS location not ready yet; this may take a few seconds...", toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }// end onClick()
        });
    }//end configureButton()

    private String createServerMessage() {
        partyName_toServer = partyNameTextEdit.getText().toString();
        String boolMess;
        if (isPrivate_toServer)
            boolMess = "true";
        else
            boolMess = "false";
        return "PartyName: " + partyName_toServer + "\nLat: " + latitude_toServer + "\nLong: " + longitude_toServer + "\nPrivate?: " + boolMess;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng hostLoc = new LatLng(latitude_toServer,longitude_toServer);
        mMap.addMarker(new MarkerOptions().position(hostLoc).title("PARTY AT MY HOUSE!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hostLoc));
    }
}
