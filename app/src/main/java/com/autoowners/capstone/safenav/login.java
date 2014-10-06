package com.autoowners.capstone.safenav;

import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class login extends ActionBarActivity {
    final String TAG = "HomeActivity.java";
    MyLocationListener locationListener = null;
    public final static String USER_NAME = "com.autoowners.capstone.safenav";
    private EditText  username=null;
    private EditText  password=null;
    private TextView attempts;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get current GPS location
        LocationManager locationManager = (LocationManager)
                getSystemService(this.LOCATION_SERVICE);
        postPostion();
        locationListener = new MyLocationListener(this);
        Log.d(TAG, "Location listener created");

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

        // Launch view
        setContentView(R.layout.activity_login);
    }

    private void postPostion()
    {

        final String url = "http://35.9.22.107:248/api/History/";
        HttpRequest httpRequest = new HttpRequest();
        JSONObject httpBody = new JSONObject();
        try {
            httpRequest.url = url;
            httpRequest.type = "post";
            httpBody.put("lat","42.72469536");
            httpBody.put("lng", "-84.48131172");
            httpRequest.body = httpBody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String response = new AsyncTaskConnect().execute(httpRequest).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void login(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        EditText user = (EditText)findViewById(R.id.userName);
        EditText pass = (EditText)findViewById(R.id.password);
        intent.putExtra(USER_NAME, "Welcome "+user.getText().toString());
        startActivity(intent);
        /*
        Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
        */

    }
}
