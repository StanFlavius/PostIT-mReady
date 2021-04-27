package com.example.postit;

public class PostRequest {

    private String message;
    private String token;

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public PostRequest(String message, String token){
        this.message = message;
        this.token = token;
    }
}
