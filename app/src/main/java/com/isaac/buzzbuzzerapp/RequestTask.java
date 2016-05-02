package com.isaac.buzzbuzzerapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Isaac on 5/1/2016.
 */
class RequestTask extends AsyncTask<String, String, String>{

    private ProgressDialog progressDialog;
    private InputStream inputStream;
    private Context currentContext;


    private AsyncDataFetchResponse delegate = null;
    public interface AsyncDataFetchResponse {
        void processFinish(String output);
    }

    public RequestTask(Context currentContext, AsyncDataFetchResponse delegate){
        this.currentContext = currentContext;
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... uri) {
        HttpURLConnection httpclient = null;
        String responseString = null;

        try {
            URL url = new URL(uri[0]);
            httpclient = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(httpclient.getInputStream());
            responseString = convertStreamToString(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(httpclient != null)
                httpclient.disconnect();
        }

        return responseString;
    }

    /*
    This method was found from http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
     */
    private static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
        progressDialog.dismiss();
    }
}
