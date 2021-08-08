package com.example.chatapp.the_class;

import java.io.Serializable;

public class The_status implements Serializable {


    String image_status = "";
    String time_for_the_image = "";
    String name ="";

    public String getImage_status() {
        return image_status;
    }

    public void setImage_status(String image_status) {
        this.image_status = image_status;
    }

    public String getTime_for_the_image() {
        return time_for_the_image;
    }

    public void setTime_for_the_image(String time_for_the_image) {
        this.time_for_the_image = time_for_the_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "The_status{" +
                "image_status='" + image_status + '\'' +
                ", time_for_the_image='" + time_for_the_image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
