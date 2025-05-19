package com.example.piazza.notificationservice.dto;

public class UserEvent {
    private String action; // "created", "logged_in", "updated"
    private String name;
    private String email;
    private String role;
    private Long id;

    public UserEvent(String action, String name, String email ,String role,Long id) {
        this.action = action;
        this.name = name;
        this.email = email;
        this.role = role;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}