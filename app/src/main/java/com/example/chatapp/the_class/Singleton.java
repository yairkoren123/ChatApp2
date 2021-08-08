package com.example.chatapp.the_class;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Singleton {
    private static Singleton single_instance = null;

    // variable of type String
    private The_profile the_profile = new The_profile();

    private The_profile go_massage_from_find = null;


    private ArrayList<The_profile> all_account = new ArrayList<>();

    private ArrayList<The_profile> friend_of_profile_by_class = new ArrayList<>();

    private ArrayList<The_status> click_status_array = new ArrayList<>();


    // private constructor restricted to this class itself
    private Singleton() {
    }

    // static method to create instance of Singleton class
    public static Singleton getInstance() {
        if (single_instance == null)
            single_instance = new Singleton();
        return single_instance;
    }

    public The_profile getThe_profile() {
        return the_profile;
    }

    public void setThe_profile(The_profile the_profile) {
        this.the_profile = the_profile;
    }

    public ArrayList<The_profile> getAll_account() {
        return all_account;
    }

    public void setAll_account(ArrayList<The_profile> all_account) {
        this.all_account = all_account;
    }

    public The_profile getGo_massage_from_find() {
        return go_massage_from_find;
    }

    public void setGo_massage_from_find(The_profile go_massage_from_find) {
        this.go_massage_from_find = go_massage_from_find;
    }

    public ArrayList<The_profile> getFriend_of_profile_by_class() {
        return friend_of_profile_by_class;
    }

    public void setFriend_of_profile_by_class(ArrayList<The_profile> friend_of_profile_by_class) {
        this.friend_of_profile_by_class = friend_of_profile_by_class;
    }

    public ArrayList<The_status> getClick_status_array() {
        return click_status_array;
    }

    public void setClick_status_array(ArrayList<The_status> click_status_array) {
        this.click_status_array = click_status_array;
    }
}
