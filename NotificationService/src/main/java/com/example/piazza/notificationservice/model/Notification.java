package com.example.piazza.notificationservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;

    private String userId;
    private String type;
    private String message;
    private boolean read;
    private boolean archived;
    private LocalDateTime timestamp;

    public Notification() {
        this.timestamp = LocalDateTime.now();
        this.read = false;
        this.archived = false;
    }

    public Notification(String message, String id, String userId, String type,  boolean archived, LocalDateTime timestamp) {
        this.message = message;
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.archived = archived;
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isRead() {
        return read;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
