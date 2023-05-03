package com.example.auditorapp.entity.drafts;

import com.example.auditorapp.entity.DraftStatus;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Drafts {
    private long id;
    @SerializedName("author")
    private String reviewAuthor;
    private String title;
    @SerializedName("text")
    private String textReview;
    private String image;
    @SerializedName("address")
    private String location;
    private Date date;
    private DraftStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DraftStatus getStatus() {
        return status;
    }

    public void setStatus(DraftStatus status) {
        this.status = status;
    }
}
