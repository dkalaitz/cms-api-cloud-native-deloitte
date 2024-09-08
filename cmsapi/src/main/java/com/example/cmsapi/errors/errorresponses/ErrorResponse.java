package com.example.cmsapi.errors.errorresponses;

public class ErrorResponse {
    private String error;
    private String message;

    public ErrorResponse(String errorCode, String message) {
        this.error = errorCode;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}