package com.farrutaps.tqhapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Status {

    private Map<Options,Boolean> statusInfoMap;

    public Status() {
        this.statusInfoMap = new HashMap<>();
        for(Options op : Options.values())
            this.statusInfoMap.put(op, false);
    }

    public void setStatusInfoMap(List<Boolean> ledStates) {
        for(int i=0; i<Options.values().length; i++)
            setOnToOption(Options.values()[i], ledStates.get(i));
    }

    public List<Boolean> getLedStatesFromStatusInfoMap() {
        List<Boolean> ledStates = new ArrayList<>();
        for(int i=0; i<Options.values().length; i++)
            ledStates.add(getOnFromOption(Options.values()[i]));
        return ledStates;
    }

    public boolean getOnFromOption(Options op) {
        return statusInfoMap.get(op);
    }

    public void setOnToOption(Options op, Boolean on) {
        statusInfoMap.put(op, on);
    }
}
