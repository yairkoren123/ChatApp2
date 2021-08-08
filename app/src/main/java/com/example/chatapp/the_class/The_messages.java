package com.example.chatapp.the_class;

public class The_messages {

    String text = "";

    String time = "";

    String to_user_phone = "";

    String from_user_phone = "";

    public The_messages(String text) {
        this.text = text;
    }

    public The_messages() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTo_user_phone() {
        return to_user_phone;
    }

    public void setTo_user_phone(String to_user_phone) {
        this.to_user_phone = to_user_phone;
    }

    public String getFrom_user_phone() {
        return from_user_phone;
    }

    public void setFrom_user_phone(String from_user_phone) {
        this.from_user_phone = from_user_phone;
    }
}

