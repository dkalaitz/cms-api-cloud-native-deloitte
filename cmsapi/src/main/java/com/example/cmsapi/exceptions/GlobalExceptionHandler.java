package com.example.cmsapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ImageExceptions.ImageNotFoundException.class, ArticleExceptions.ArticleNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageExceptions.ImageExistsException.class)
    public ResponseEntity<String> handleImageExistsException(Exception ex){
        return new ResponseEntity<>("Image already exists", HttpStatus.CONFLICT);
    }

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
}
