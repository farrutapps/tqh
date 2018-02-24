package com.farrutaps.tqhapp.client;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Sònia Batllori on 23/02/2018.
 */
public class AsyncConnection extends AsyncTask<Void,Void,String>
{
    private Context mContext;

    public AsyncConnection(Context mContext){
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(Void ... params) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return WebServices.sendRequest(WebServices.Request.POST);
        }
        return null;
    }
}

