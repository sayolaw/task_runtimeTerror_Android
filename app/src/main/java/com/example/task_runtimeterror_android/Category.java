package com.example.task_runtimeterror_android;

public class Category {
    private int id;
    private String name, category, dateCreated, dateCompleted;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
