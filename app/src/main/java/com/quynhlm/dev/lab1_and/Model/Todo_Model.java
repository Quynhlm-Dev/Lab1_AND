package com.quynhlm.dev.lab1_and.Model;

public class Todo_Model {
    private int id;
    private String title;
    private String contact;
    private String date;
    private String type;

    public Todo_Model(int id, String title, String contact, String date, String type) {
        this.id = id;
        this.title = title;
        this.contact = contact;
        this.date = date;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
