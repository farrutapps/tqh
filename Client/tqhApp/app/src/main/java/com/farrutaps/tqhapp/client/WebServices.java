package com.farrutaps.tqhapp.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sònia Batllori on 23/02/2018.
 */
public class WebServices {

    private static final int CONNECTION_TIMEOUT = 5000;
    private static final String URI = "http://esteful.feste-ip.net:57935";//"http://192.168.0.59:6969";

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

    private static String sendPost(HttpURLConnection conn, String data) throws Exception {

        // Add POST parameters
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        // Write POST data to the request
        write(conn,data);

        // Establish the connection with the server
        conn.connect();

        // Get the response (not used)
        getResponse(conn, HttpURLConnection.HTTP_ACCEPTED);
        return "" + conn.getResponseCode();
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

        String result = "" + responseCode;
        //result = "HTTP ResponseCode: " + responseCode + " " + conn.getResponseMessage() + "\n";

        if(responseCode == expectedResponseCode) {
            result += read(conn);
            conn.disconnect();
        }
        return result;
    }

    public static String sendRequest(Request request, String data) {
        String response = null;
        try {
            HttpURLConnection conn = createHttpURLConnection(request);
            response = (request == Request.GET) ? sendGet(conn) : sendPost(conn, data);

        } catch (Exception e) {}

        return response;
    }
}
