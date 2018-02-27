package com.farrutaps.tqhapp.controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.farrutaps.tqhapp.Adapters.MyStatusAdapter;
import com.farrutaps.tqhapp.Adapters.StatusAdapter;
import com.farrutaps.tqhapp.R;
import com.farrutaps.tqhapp.client.AsyncConnection;
import com.farrutaps.tqhapp.client.PayloadItem;
import com.farrutaps.tqhapp.model.Options;
import com.farrutaps.tqhapp.model.User;
import com.farrutaps.tqhapp.view.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static List<User> users;
    private static User master;
    private static Activity mainActivity;
    private static View view;
    private static StatusAdapter mainAdapter;
    private static MyStatusAdapter userAdapter;
    private static boolean enableToast;

    public Controller(Activity activity) {
        mainActivity = activity;
        view = mainActivity.getWindow().getDecorView().getRootView();
        users = new ArrayList<>();
        users.add(new User(view.getResources().getString(R.string.user0)));
        users.add(new User(view.getResources().getString(R.string.user1)));
        enableToast = true;
    }

    public static boolean isToastEnabled() {
        return enableToast;
    }

    public static void enableToast(boolean enable){
        enableToast = enable;
    }

    public static void showToast(Context context, String text) {
        if(isToastEnabled())
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void setMainAdapter(StatusAdapter adapter) {
        mainAdapter = adapter;
    }

    public static void setUserAdapter(MyStatusAdapter adapter) {
        userAdapter = adapter;
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

    public static void refreshUserBackHomeLeds(User user) {
        ImageView[] leds = MainActivity.PlaceholderFragment.getLedsUser(getUsers().indexOf(user));
        View view = MainActivity.PlaceholderFragment.getRootView();

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

    public static void refreshBackHomeLeds() {
        for (int i=0; i<getUsers().size(); i++)
            refreshUserBackHomeLeds(getUsers().get(i));
    }

    public static void sendPost(boolean reset) {
        if(reset)
            runConnectionThread(sendResetDataToEsteful());
        else
            runConnectionThread(sendDataToEsteful());
    }

    public static String sendResetDataToEsteful() {
        List<PayloadItem> payloadItems = new ArrayList<>();
        for(int i=0; i<getUsers().size(); i++)
            payloadItems.add(new PayloadItem(i));

        Gson data = new Gson();
        return data.toJson(payloadItems);
    }

    private static String sendDataToEsteful() {

        List<PayloadItem> payload = new ArrayList<>();
        PayloadItem payloadItem = new PayloadItem(getMasterId());
        payloadItem.setStates(getMaster().getStatus().getStatesFromStatusInfoMap());
        payloadItem.setTime(getMaster().getBackHome());
        payload.add(payloadItem);

        Gson data = new Gson();
        return data.toJson(payload);
    }

    public static void sendGet() {
        runConnectionThread(null);
    }

    public static void getDataFromEsteful(String data) {
        List<PayloadItem> response = PayloadItem.parseJSON(data);
        for(int i = 0; i < response.size(); i++) {
            int id = response.get(i).getUserId();
            int time = response.get(i).getTime();
            List<Boolean> states = response.get(i).getStates();

            User user = getUsers().get(id);
            user.setBackHome(time);
            user.getStatus().setStatusInfoMap(states);
        }
        mainAdapter.notifyDataSetChanged();
        userAdapter.notifyDataSetChanged();
        refreshBackHomeLeds();
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
