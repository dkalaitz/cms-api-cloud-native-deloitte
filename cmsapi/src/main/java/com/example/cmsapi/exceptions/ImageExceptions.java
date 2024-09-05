package com.example.cmsapi.exceptions;

public class ImageExceptions {

    public static class ImageNotFoundException extends RuntimeException{
        public ImageNotFoundException(String message) {
            super(message);
        }
    }

    public static class ImageExistsException extends RuntimeException{
        public ImageExistsException() {
            super();
        }
    }
}
