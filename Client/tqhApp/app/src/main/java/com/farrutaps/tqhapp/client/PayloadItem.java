package com.farrutaps.tqhapp.client;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Sònia Batllori on 26/02/2018.
 */
public class PayloadItem {

    private int userId;

    private List<Boolean> ledStates;

    private int time;

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
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Type collectionType = new TypeToken<List<PayloadItem>>(){}.getType();
        Gson gson = gsonBuilder.create();
        List<PayloadItem> result = gson.fromJson(response, collectionType);
        return result;
    }
}
