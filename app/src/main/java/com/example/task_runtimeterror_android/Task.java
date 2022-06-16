package com.example.task_runtimeterror_android;

public class Task {
    private int id, status,isSub;
    private String name, category, dateCreated, dateCompleted;

    public Task(int id, String name,int status, String category,int isSub, String dateCreated, String dateCompleted) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.isSub = isSub;
        this.category = category;
        this.dateCreated = dateCreated;
        this.dateCompleted = dateCompleted;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public int getIsSub() {
        return isSub;
    }

    public int getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }
}

