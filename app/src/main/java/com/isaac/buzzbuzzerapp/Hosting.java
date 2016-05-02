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
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;

import org.w3c.dom.Text;


public class Hosting extends AppCompatActivity {

    protected static final String TAG = "Host Activity ";
    protected static final int GPS_MIN_DIST_IN_METERS = 1000;
    protected static final int GPS_MIN_TIME_IN_MILLISEC = 5000;
    LocationManager locationManager;
    /* Location of last known location of the user*/
    Location mLastLocation;

    /* Latitude of the location of last known location of the user*/
    String mLatitudeText;

    /* Longitude of the location of last known location of the user*/
    String mLongitudeText;
    TextView latitudeTextV;
    TextView longitudeTextV;
    Button hostButton;
    String provider = locationManager.GPS_PROVIDER;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosting);

        //Layout stuff
        longitudeTextV = (TextView) findViewById(R.id.longitudeTextV);
        latitudeTextV = (TextView) findViewById(R.id.latitudeTextV);
        hostButton = (Button) findViewById(R.id.hostButton);
        // Location stuff
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //mLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                latitudeTextV.setText("" + location.getLatitude());
                longitudeTextV.setText("" + location.getLongitude());
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
        locationManager.requestLocationUpdates(provider, GPS_MIN_TIME_IN_MILLISEC, GPS_MIN_DIST_IN_METERS, locationListener);
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


    public void configureButton() {
        hostButton.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ResourceType")
            public void onClick(View v) {

                locationManager.requestLocationUpdates(provider, GPS_MIN_TIME_IN_MILLISEC, GPS_MIN_DIST_IN_METERS, locationListener);
            }
        });
    }
}