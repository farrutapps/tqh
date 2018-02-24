package com.farrutaps.tqhapp.controller;

import com.farrutaps.tqhapp.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static List<User> users;
    private static User master;

    public Controller()
    {
        users = new ArrayList<>();
        users.add(new User("WZ"));
        users.add(new User("FK"));
    }


    public static void setMaster(User user) {
        master = user;
    }

    public static User getMaster() {
        return master;
    }

    public static List<User> getUsers() {
        return users;
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
