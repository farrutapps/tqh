package com.farrutaps.tqhapp.controller;

import com.farrutaps.tqhapp.model.User;

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
}
