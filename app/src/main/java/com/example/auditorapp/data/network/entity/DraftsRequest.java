package com.example.auditorapp.data.network.entity;

public class DraftsRequest {
    private String objectId;
    private String title;
    private String text;
    private String address;
    private String author;


    public DraftsRequest( String author, String title, String text) {
        this.author = author;
        this.title = title;
        this.text = text;

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
