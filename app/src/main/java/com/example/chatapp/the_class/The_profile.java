package com.example.chatapp.the_class;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class The_profile {

    String last_connected = "";
    String name = "";
    String phone = "";
    String image = "";
    boolean now_connected = true;

    ArrayList<String> friend_of_the_profile = new ArrayList<>();
    ArrayList<The_status> statuses_of_the_profile = new ArrayList<>();
    String the_profile_status_short = "";

    @Override
    public String toString() {
        return "The_profile{" +
                "last_connected='" + last_connected + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", image='" + image + '\'' +
                ", now_connected=" + now_connected +
                ", friend_of_the_profile=" + friend_of_the_profile +
                ", statuses_of_the_profile=" + statuses_of_the_profile +
                ", the_profile_status_short='" + the_profile_status_short + '\'' +
                '}';
    }

    public ArrayList<The_status> getStatuses_of_the_profile() {
        return statuses_of_the_profile;
    }

    public void setStatuses_of_the_profile(ArrayList<The_status> statuses_of_the_profile) {
        this.statuses_of_the_profile = statuses_of_the_profile;
    }

    public String getLast_connected() {
        return last_connected;
    }

    public void setLast_connected(String last_connected) {
        this.last_connected = last_connected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getFriend_of_the_profile() {
        return friend_of_the_profile;
    }

    public void setFriend_of_the_profile(ArrayList<String> friend_of_the_profile) {
        this.friend_of_the_profile = friend_of_the_profile;
    }

    public String getThe_profile_status_short() {
        return the_profile_status_short;
    }

    public void setThe_profile_status_short(String the_profile_status_short) {
        this.the_profile_status_short = the_profile_status_short;
    }

    public boolean isNow_connected() {
        return now_connected;
    }

    public void setNow_connected(boolean now_connected) {
        this.now_connected = now_connected;
    }
}
