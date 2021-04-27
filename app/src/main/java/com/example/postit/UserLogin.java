package com.example.postit;

public class UserLogin {
    private String Username;

    private String Password;

    public UserLogin(String username, String password){
        this.Username = username;
        this.Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }
}
