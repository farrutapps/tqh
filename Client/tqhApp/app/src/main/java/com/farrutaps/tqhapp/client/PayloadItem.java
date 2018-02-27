package com.farrutaps.tqhapp.client;

import com.farrutaps.tqhapp.model.Options;
import com.google.gson.FieldNamingPolicy;
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
    @SerializedName("led_states")
    private List<Boolean> ledStates;
    @SerializedName("time")
    private int time;

    public PayloadItem(int userId) {
        this.userId = userId;
        this.time = 0;
        this.ledStates = new ArrayList<>();
        for(int i = 0; i< Options.values().length; i++)
            this.ledStates.add(false);
    }

    public int getUserId() {
        return userId;
    }

    public List<Boolean> getLedStates() {
        return ledStates;
    }

    public int getTime() {
        return time;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLedStates(List<Boolean> ledStates) {
        this.ledStates = ledStates;
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
