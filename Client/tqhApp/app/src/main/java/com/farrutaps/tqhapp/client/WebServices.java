package com.farrutaps.tqhapp.client;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sònia Batllori on 23/02/2018.
 */
public class WebServices {

    private static final int CONNECTION_TIMEOUT = 5000;
    private static final String URL_TEST = "https://www.android.com";
    private static final String URL_GET = "http://http://10.0.0.23:6969/status";
    private static final String URL_POST = "http://http://10.0.0.23:6969/update";

    public static String getStatus() {
        String result = "Error";

        int responseCode = -1;
        String responseBody = "Error";
        try {

            // Send a GET Request to the URL_GET URL
            URL url = new URL(URL_TEST);
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setConnectTimeout(CONNECTION_TIMEOUT);
            urlc.setRequestMethod("GET");
            urlc.connect();
            responseCode = urlc.getResponseCode();
            if (urlc.getResponseCode() == 200) {
                urlc.disconnect();
            }

            // Read the Response
            BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
            responseBody = response.toString();
            // TODO readGETResponse(responseBody);

        } catch (Exception e) {}

        result = "HTTP Response Code: "
                + responseCode + " " + HTTP_1_0_Response.getResponseFromCode(responseCode).getResponseMessage()
                + "\nHTTP Response Body: " + responseBody;

        return result;
    }

    /*
        [
            {
                "user_id": 0,
                "led_states": [false, true, false, true, ...],
                "time":7
            },
            {
                "user_id": 1,
                "led_states": [false, true, false, true, ...],
                "time":8
            }
        ]

     */
    public static String readGETResponse(String in) throws JSONException {
        String test = "";
        in = "[{\"user_id\": 0, \"led_states\": [0, 1, 0, 0, 0, 0, 0, 1, 0],\"time\":7},{\"user_id\": 1, \"led_states\": [1, 1, 1, 0, 0, 0, 0, 0, 0], \"time\":8}]\n";
        JSONArray statusJSCONArray = new JSONArray(in);
        for (int i = 0; i < statusJSCONArray.length(); i++)
        {
            JSONObject status = statusJSCONArray.getJSONObject(i);
            int userId = status.getInt("user_id");
            JSONArray ledStatesJSONArray = status.getJSONArray("led_states");
            int time = status.getInt("time");
            test += userId + " " + time + " " + ledStatesJSONArray + "\n";
        }
        return test;
    }
}

/*
    private static final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    public static String sendGet(View view) throws Exception {

        String url = "https://www.android.com";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        con.setConnectTimeout(5000);
        con.connect();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }

    // HTTP POST request
    public static void sendPost() throws Exception {

        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}*/
