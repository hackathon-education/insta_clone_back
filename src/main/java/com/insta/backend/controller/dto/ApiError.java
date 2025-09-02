package com.insta.backend.controller.dto;

public class ApiError {
    private int code;
    private String message;
    private Object details;

    public ApiError(int code, String message, Object details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getDetails() {
        return details;
    }
}
