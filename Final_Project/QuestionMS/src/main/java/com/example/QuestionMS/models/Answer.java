package com.example.QuestionMS.models;

import java.time.LocalDateTime;

public class Answer {

    private String id;
    private String content;
    private float  rating;
    private int ratingCount;
    private LocalDateTime createdDate;

    public Answer(String content) {
        this.content = content;
        this.ratingCount = 0;
        this.rating = 0;
        this.createdDate = LocalDateTime.now();
    }

    public Answer(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}



