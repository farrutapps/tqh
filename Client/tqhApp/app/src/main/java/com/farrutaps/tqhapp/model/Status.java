package com.farrutaps.tqhapp.model;

import java.util.HashMap;
import java.util.Map;

public class Status {

    private Map<Options,Boolean> statusInfoMap;

    public Status() {
        this.statusInfoMap = new HashMap<>();
        for(Options op : Options.values())
            this.statusInfoMap.put(op, false);
    }

    public Map<Options, Boolean> getStatusInfoMap() {
        return statusInfoMap;
    }

    public boolean getOnFromOption(Options op) {
        return statusInfoMap.get(op);
    }

    public void setOnToOption(Options op, Boolean on) {
        statusInfoMap.put(op, on);
    }
}
