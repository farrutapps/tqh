package com.farrutaps.tqhapp.client;

import com.farrutaps.tqhapp.model.Options;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sònia Batllori on 26/02/2018.
 */
public class PayloadItem {

    @SerializedName("user_id")
    private int userId;
    @SerializedName("states")
    private List<Boolean> states;
    @SerializedName("time")
    private int time;

    public PayloadItem(int userId) {
        this.userId = userId;
        this.time = 0;
        this.states = new ArrayList<>();
        for(int i = 0; i< Options.values().length; i++)
            this.states.add(false);
    }

    public int getUserId() {
        return userId;
    }

    public List<Boolean> getStates() {
        return states;
    }

    public int getTime() {
        return time;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStates(List<Boolean> states) {
        this.states = states;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public static List<PayloadItem> parseJSON(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Type collectionType = new TypeToken<List<PayloadItem>>(){}.getType();
        Gson gson = gsonBuilder.create();
        List<PayloadItem> result = gson.fromJson(response, collectionType);
        return result;
    }
}
