package com.example.guest.discussionforum.models;

public class Category {
    String id;
    String name;

    public Category() { }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String key) {
        this.name = name;
        this.id = key;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
}
