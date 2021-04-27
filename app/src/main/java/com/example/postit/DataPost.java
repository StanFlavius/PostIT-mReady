package com.example.postit;

public class DataPost {

    private Integer id;
    private String display_name;
    private Integer user_id;
    private String message;
    private String created_at;

    public Integer getId() {
        return id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getMessage() {
        return message;
    }

    public String getCreated_at() {
        return created_at;
    }
}
