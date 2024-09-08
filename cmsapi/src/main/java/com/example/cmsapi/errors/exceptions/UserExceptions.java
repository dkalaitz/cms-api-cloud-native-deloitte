package com.example.cmsapi.errors.exceptions;

public class UserExceptions {

    public static class UsernameExistsException extends RuntimeException {
        public UsernameExistsException(String message) {
            super(message);
        }
    }

}
