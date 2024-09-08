package com.example.cmsapi.errors.responses;

import java.util.List;

public class ValidationErrorResponse {
    private String error;
    private List<String> messages;

    public ValidationErrorResponse() {
    }

    public ValidationErrorResponse(String error, List<String> messages) {
        this.error = error;
        this.messages = messages;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}

