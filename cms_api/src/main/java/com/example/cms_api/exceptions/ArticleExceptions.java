package com.example.cms_api.exceptions;

public class ArticleExceptions extends RuntimeException{

    public static class ArticleNotFoundException extends RuntimeException {
        public ArticleNotFoundException(String message) {
            super(message);
        }
    }

}
