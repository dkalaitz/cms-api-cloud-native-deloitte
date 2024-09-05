package com.example.cmsapi.exception;

public class UserExceptions {

    public static class UsernameExistsException extends RuntimeException {
        public UsernameExistsException(String message) {
            super();
        }
    }

}
