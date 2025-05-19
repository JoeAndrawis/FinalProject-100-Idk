package com.example.UserMS.rabbitmq;

import java.io.Serializable;

public class UserEvent implements Serializable {

    private String action; // "created", "logged_in", "updated"
    private String name;
    private String email;
    private String role;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private Long id;

    public UserEvent (String action, String name, String email ,String role,Long id) {
        this.action = action;
        this.name = name;
        this.email = email;
        this.role = role;
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
