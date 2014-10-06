package com.autoowners.capstone.safenav;

/**
 * Created by timsloncz on 10/6/14.
 */
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

// you can make this class as another java file so it will be separated from your main activity.
public class AsyncTaskConnect extends AsyncTask<HttpRequest, String, String> {
    final String TAG = "AsyncTaskParseJson.java";
    @Override
    protected void onPreExecute() {}

    @Override
    protected String doInBackground(HttpRequest... request) {
        String response = "";
        // instantiate our json parser
        JsonParser jParser = new JsonParser();
        // get json string from url
        if(request[0].type.equals("post")) {
            response = jParser.postJsonBody(request[0]);
        }

        return response;
    }
    @Override
    protected void onPostExecute(String strFromDoInBg) {
        String returnString = strFromDoInBg;
    }
}
