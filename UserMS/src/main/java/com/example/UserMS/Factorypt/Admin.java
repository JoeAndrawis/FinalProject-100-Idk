
package com.example.UserMS.Factorypt;

public class Admin implements UserInterface {
    private String name;
    private String email;
    private String password;

    public Admin(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getRole() { return "admin"; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
