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
            return WebServices.getStatus();
        }
        return null;
            /*try {
                URL url = new URL("https://www.android.com");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(5000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    urlc.disconnect();
                    return true;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;*/
    }
}

