package com.farrutaps.tqhapp.controller;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.farrutaps.tqhapp.R;
import com.farrutaps.tqhapp.client.AsyncConnection;
import com.farrutaps.tqhapp.model.Options;
import com.farrutaps.tqhapp.model.User;
import com.farrutaps.tqhapp.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {

    private static List<User> users;
    private static User master;
    private static View view;
    private static Activity mainActivity;

    public Controller(Activity activity)
    {
        mainActivity = activity;
        view = mainActivity.getWindow().getDecorView().getRootView();
        users = new ArrayList<>();
        users.add(new User(view.getResources().getString(R.string.user0)));
        users.add(new User(view.getResources().getString(R.string.user1)));
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

    public static String sendDataToEsteful() {
        try
        {
            // Create the json object to send the data
            JSONArray resultList = new JSONArray();
            JSONObject data = new JSONObject();
            JSONArray ledStates = new JSONArray();

            data.put(Parameters.USER_ID.name().toLowerCase(), getMasterId());
            data.put(Parameters.TIME.name().toLowerCase(), getMaster().getBackHome());
            for(int i=0; i<getMaster().getStatus().getStatusInfoMap().keySet().size(); i++)
                ledStates.put(i, getMaster().getStatus().getOnFromOption(Options.values()[i]));
            data.put(Parameters.LED_STATES.name().toLowerCase(), ledStates);
            resultList.put(0, data);

            // Send data to the server
            runConnectionThread(resultList.toString());

            return view.getResources().getString(R.string.sent_status);
        }
        catch(Exception e)
        {
            return view.getResources().getString(R.string.any_error);
        }
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
                                asyncConnection.execute(data);
                            } catch (Exception e) {
                            }
                        }
                    });
                } catch (Exception e) {}
            }
        }.start();
    }
}
