package com.autoowners.capstone.safenav;
/**
 * Created by timsloncz on 10/6/14.
 *
 * Handles user locations
 */
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/*---------- Listener class to get coordinates ------------- */
public class MyLocationListener implements LocationListener {
    final String TAG = "MyLocationListener.java";
    final String url = "http://35.9.22.107:248/api/History/";
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    // current latitude
    public double mLat;

    // current longitude
    public double mLng;

    // The activity in which this listener was created
    Activity mActivity;

    /* Explicit Constructor */
    MyLocationListener(Activity activity){
        mActivity = activity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        mLat = loc.getLatitude();
        mLng = loc.getLongitude();
        Log.d(TAG, "New Coords: " + mLat + "," + mLng);
        postPostion();
    }
    private void postPostion()
    {
        HttpRequest httpRequest = new HttpRequest();
        JSONObject httpBody = new JSONObject();
        try {
            httpRequest.url = url;
            httpRequest.type = "post";
            httpBody.put("lat",mLat);
            httpBody.put("lng", mLng);
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
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    /** Determines whether one Location reading is better than the current Location fix
     * @param location  The new Location that you want to evaluate
     * @param currentBestLocation  The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    public String getLat()
    {
        return Double.toString(mLat);
    }
    public String getLng()
    {
        return Double.toString(mLat);
    }
}

