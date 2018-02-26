package com.farrutaps.tqhapp.controller;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.farrutaps.tqhapp.Adapters.StatusAdapter;
import com.farrutaps.tqhapp.R;
import com.farrutaps.tqhapp.client.AsyncConnection;
import com.farrutaps.tqhapp.client.PayloadItem;
import com.farrutaps.tqhapp.model.Options;
import com.farrutaps.tqhapp.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static List<User> users;
    private static User master;
    private static Activity mainActivity;
    private static View view;
    private static StatusAdapter mainAdapter;


    public Controller(Activity activity)
    {
        mainActivity = activity;
        view = mainActivity.getWindow().getDecorView().getRootView();
        users = new ArrayList<>();
        users.add(new User(view.getResources().getString(R.string.user0)));
        users.add(new User(view.getResources().getString(R.string.user1)));
    }

    public static void setMainAdapter(StatusAdapter adapter) {
        mainAdapter = adapter;
    }

    public static void setMaster(User user) {
        master = user;
    }

    public static User getMaster() {
        return master;
    }

    public static int getMasterId() {
        return getUsers().indexOf(getMaster());
    }

    public static List<User> getUsers() {
        return users;
    }

    public static Options[] getOptions() {
        return Options.values();
    }

    public static void setBackHome(int backHome) {
        getMaster().setBackHome(backHome);
    }

    public static void refreshUserBackHomeLeds(User user, ImageView[] leds, View view) {
        // Get the decimal hour in binary base with 4 digits
        String binStr = Integer.toBinaryString(user.getBackHome());
        while (binStr.length() < 4) {
            binStr = "0" + binStr;
        }

        // Turn the LEDs on or off depending on the binary result
        for(int i = 0; i < binStr.length(); i++) {
            if (binStr.charAt(i) == '1')
                leds[i].setBackground(view.getResources().getDrawable(R.drawable.led_on));
            else
                leds[i].setBackground(view.getResources().getDrawable(R.drawable.led_off));
        }
    }

    public static void sendPost() throws Exception{
        runConnectionThread(sendDataToEsteful());
    }

    private static String sendDataToEsteful() throws Exception {

        // TODO use GSON instead JSON
        // Create the json object to send the data
        JSONArray resultList = new JSONArray();
        JSONObject data = new JSONObject();
        JSONArray ledStates = new JSONArray();

        data.put(Parameters.USER_ID.name().toLowerCase(), getMasterId());
        data.put(Parameters.TIME.name().toLowerCase(), getMaster().getBackHome());
        for(int i=0; i<getOptions().length; i++)
            ledStates.put(i, getMaster().getStatus().getOnFromOption(getOptions()[i]));
        data.put(Parameters.LED_STATES.name().toLowerCase(), ledStates);
        resultList.put(0, data);

        return resultList.toString();
    }

    public static void sendGet() {
        runConnectionThread(null);
    }

    public static void getDataFromEsteful(String data) {
        List<PayloadItem> response = PayloadItem.parseJSON(data);
        for(int i = 0; i < response.size(); i++) {
            int id = response.get(i).getUserId();
            int time = response.get(i).getTime();
            List<Boolean> ledStates = response.get(i).getLedStates();

            User user = getUsers().get(id);
            user.setBackHome(time);

            // TODO debug that
            for(int j=0; j < getOptions().length; j++)
                user.getStatus().setOnToOption(getOptions()[j],ledStates.get(j));
        }
        mainAdapter.notifyDataSetChanged();
    }

    private static void runConnectionThread(final String data) {
        new Thread() {
            public void run() {
                try {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                AsyncConnection asyncConnection = new AsyncConnection(mainActivity);
                                if(data != null)
                                    asyncConnection.execute(data);
                                else
                                    asyncConnection.execute();
                            } catch (Exception e) {
                            }
                        }
                    });
                } catch (Exception e) {}
            }
        }.start();
    }
}
