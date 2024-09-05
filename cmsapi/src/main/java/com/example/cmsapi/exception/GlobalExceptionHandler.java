package com.example.cmsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Not Found Exceptions
    @ExceptionHandler({ImageExceptions.ImageNotFoundException.class, ArticleExceptions.ArticleNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle Conflict Exceptions
    @ExceptionHandler({ImageExceptions.ImageExistsException.class, UserExceptions.UsernameExistsException.class})
    public ResponseEntity<String> handleImageExistsException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> errorMessages = new ArrayList<>();

        // Extract all validation errors and collect their default messages
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                String errorMessage = error.getDefaultMessage();
                errorMessages.add(errorMessage);
            }
        });

        // Return the list of error messages with HTTP 400 Bad Request status
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    // Handle AuthenticationException and its subclasses
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        String errorMessage = "Invalid username or password.";
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    // Handle NoSuchElementException when user is not found
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        String errorMessage = "User not found.";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    // Handle IllegalArgumentException for null username or password
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String errorMessage = "Invalid input provided.";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
