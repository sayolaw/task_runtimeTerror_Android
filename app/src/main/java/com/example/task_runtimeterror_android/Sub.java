package com.example.task_runtimeterror_android;

public class Sub {
    private int id,taskid,status;
    private String name;

    public Sub(int id,int status, String name) {
        this.id = id;
        this.status = status;
        this.name = name;

    }

    public int getId() {
        return id;
    }
    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
