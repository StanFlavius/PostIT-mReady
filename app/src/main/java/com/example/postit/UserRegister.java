package com.example.postit;

import com.google.gson.annotations.SerializedName;

public class UserRegister {
    private String Username;

    private String Password;

    private String Display_name;

    public UserRegister(String username, String password, String display_name){
        this.Username = username;
        this.Password = password;
        this.Display_name = display_name;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getDisplay_name() {
        return Display_name;
    }
}
