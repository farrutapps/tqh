package com.farrutaps.tqhapp.client;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.farrutaps.tqhapp.R;
import com.farrutaps.tqhapp.controller.Controller;

import java.net.HttpURLConnection;

/**
 * Created by Sònia Batllori on 23/02/2018.
 */
public class AsyncConnection extends AsyncTask<String, Void, String> {
    private Context mContext;

    public AsyncConnection(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            String responseCode = result.substring(0, 3);
            result = (responseCode.equals("200")) ? result.substring(3, result.length()) : null;

            if(responseCode.charAt(0) == '2')
                Controller.showToast(mContext, mContext.getString(R.string.received_status));
            else
                Controller.showToast(mContext, mContext.getString(R.string.any_error));

            if(result != null)
                Controller.getDataFromEsteful(result);

        } catch (Exception e) {
            Controller.showToast(mContext,mContext.getString(R.string.error_esteful_not_available));
        }
    }

    @Override
    protected String doInBackground(String... params) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            WebServices.Request request;
            String data = null;
            try {
                data = params[0];
                request = WebServices.Request.POST;
            } catch (Exception e)
            {
                request = WebServices.Request.GET;
            }
            return WebServices.sendRequest(request, data);
        }
        return null;
    }
}

