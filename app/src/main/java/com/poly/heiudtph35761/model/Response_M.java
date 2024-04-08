package com.poly.heiudtph35761.model;

public class Response_M<T> {
    private int status;
    private String message;
    private T data;

    private String token;
    private String refreshToken;

    public Response_M(int status, String message, T data, String token, String refreshToken) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
