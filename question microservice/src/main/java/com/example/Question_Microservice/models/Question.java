package com.example.Question_Microservice.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document(collection = "questions")
@Data
public class Question {
    @Id
    private String id;
    private String title;
    private String content;
    private float  rating;
    private int ratingCount;
    private LocalDateTime createdTime;
    private List<Answer> answers;


    public Question(String title, String content) {
        this.title = title;
        this.content = content;
        this.rating = 0;
        this.ratingCount = 0;
        this.answers = Collections.emptyList();
        this.createdTime = LocalDateTime.now();
    }

    public Question(){

    }


    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Answer> getAnswers() {
        if(answers == null){
            answers = new ArrayList<>();
        }
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
