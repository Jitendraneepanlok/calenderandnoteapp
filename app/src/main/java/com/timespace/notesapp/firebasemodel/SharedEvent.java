package com.timespace.notesapp.firebasemodel;

public class SharedEvent {
    String title,date,name,time;

    public SharedEvent() {
    }

    public SharedEvent(String title, String date, String name, String time) {
        this.title = title;
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
