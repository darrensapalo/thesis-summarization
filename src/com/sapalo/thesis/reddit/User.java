package com.sapalo.thesis.reddit;

import java.util.HashMap;

/**
 * Created by Darren Karl Sapalo on 8/27/2016.
 */
public class User {
    private String name;
    private int rank;
    private String remarks;

    public User(String name, int rank, String remarks) {
        this.name = name;
        this.rank = rank;
        this.remarks = remarks;
    }

    private static HashMap<String, User> library = new HashMap<>();

    public static void registerUser(User user, String identifier){
        library.put(identifier, user);
    }

    public static boolean userExists(String identifier){
        return library.containsKey(identifier);
    }

    public static boolean userExists(User user){
        return library.containsKey(user);
    }

    public static User getUserCache(String identifier){
        return library.get(identifier);
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public String getRemarks() {
        return remarks;
    }

    @Override
    public String toString() {
        return name;
    }
}
