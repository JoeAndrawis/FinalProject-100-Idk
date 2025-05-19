package com.example.QuestionMS.DTO;


import java.time.Instant;

public class QuestionEvent {
    private String questionId;
    private String eventType;    // e.g. "QUESTION_CREATED", "QUESTION_UPDATED", ...
    private Instant timestamp;

    // constructors, getters, setters
    public QuestionEvent() {}
    public QuestionEvent(String questionId, String eventType, Instant timestamp) {
        this.questionId = questionId;
        this.eventType = eventType;
        this.timestamp   = timestamp;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
