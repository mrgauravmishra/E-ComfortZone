package com.example.gaurav.comfortzone.Model;

/**
 * Created by Gaurav on 3/3/2018.
 */

public class Category {
    private String Name;
    private String Image;

    public Category() {

    }

    public Category(String name, String image){
        Name = name;
        Image = image;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {

        Name = name;
    }

    public String getImage() {
        return Image;
    }
}
