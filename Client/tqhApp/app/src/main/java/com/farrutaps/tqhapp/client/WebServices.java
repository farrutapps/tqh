package com.farrutaps.tqhapp.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sònia Batllori on 23/02/2018.
 */
public class WebServices {

    private static final int CONNECTION_TIMEOUT = 5000;
    private static final String URI = "http://192.168.0.206:6969";//"http://192.168.0.59:6969";

    public enum Request {
        GET(URI + "/status"),
        POST(URI + "/update");

        private String url;
        Request(String url) {
            this.url = url;
        }
    }

    private static HttpURLConnection createHttpURLConnection(Request request) throws Exception {
        // Get the request URL
        URL url = new URL(request.url);

        // Define a connection adding the common parameters
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(CONNECTION_TIMEOUT);
        conn.setRequestMethod(request.name());

        return conn;
    }

    private static String sendGet(HttpURLConnection conn) throws Exception {

        // Establish the connection with the server
        conn.connect();

        // Get the response
        return getResponse(conn, HttpURLConnection.HTTP_OK);
    }

    private static String sendPost(HttpURLConnection conn) throws Exception {

        // Add POST parameters
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        // Write POST data to the request
        write(conn,getJSONtest());

        // Establish the connection with the server
        conn.connect();

        // Get the response
        return getResponse(conn, HttpURLConnection.HTTP_ACCEPTED);
    }

    private static String read(HttpURLConnection conn) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine = reader.readLine();
        reader.close();
        return inputLine;
    }

    private static void write(HttpURLConnection conn, String data) throws Exception {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(data);
        writer.flush();
        writer.close();
        os.close();
    }

    private static String getResponse(HttpURLConnection conn, int expectedResponseCode) throws Exception {

        int responseCode = conn.getResponseCode();

        String result;
        result = "HTTP ResponseCode: " + responseCode + " " + conn.getResponseMessage();

        if(responseCode == expectedResponseCode) {
            result += "\n" + read(conn);
            conn.disconnect();
        }
        return result;
    }

    public static String sendRequest(Request request) {
        String response = "Error";
        try {
            HttpURLConnection conn = createHttpURLConnection(request);
            response = (request == Request.GET) ? sendGet(conn) : sendPost(conn);
        } catch (Exception e) {}

        return response;
    }

    public static String getJSONtest() throws JSONException {
        JSONObject data = new JSONObject();

        data.put("user_id", 0);
        data.put("time", 8);

        JSONArray led_states = new JSONArray();
        for(int i = 0; i < 8; i++)
            led_states.put(i, true);
        data.put("led_states", led_states);

        JSONArray dataArray = new JSONArray();
        dataArray.put(0,data);

        return dataArray.toString();

    }
}
