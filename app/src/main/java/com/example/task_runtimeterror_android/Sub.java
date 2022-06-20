package com.example.task_runtimeterror_android;

public class Sub {
    private int id,taskid,status;
    private String name;

    public Sub(int id,int taskid,String name,int status) {
        this.id = id;
        this.taskid = taskid;
        this.name = name;
        this.status = status;


    }

    public int getId() {
        return id;
    }
    public int gettaskId() {
        return taskid;
    }


    public String getName() {
        return name;
    }
    public int getStatus() {
        return status;
    }
}
