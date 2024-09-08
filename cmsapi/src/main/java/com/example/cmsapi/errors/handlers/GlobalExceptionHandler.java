package com.example.cmsapi.errors.handlers;

import com.example.cmsapi.errors.exceptions.ArticleExceptions;
import com.example.cmsapi.errors.exceptions.ImageExceptions;
import com.example.cmsapi.errors.exceptions.UserExceptions;
import com.example.cmsapi.errors.responses.ErrorResponse;
import com.example.cmsapi.errors.responses.ValidationErrorResponse;
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

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Not Found Exceptions
    @ExceptionHandler({ImageExceptions.ImageNotFoundException.class, ArticleExceptions.ArticleNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception ex){
        return new ResponseEntity<>(new ErrorResponse("RESOURCE_NOT_FOUND", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    // Handle Conflict Exceptions
    @ExceptionHandler({ImageExceptions.ImageExistsException.class, UserExceptions.UsernameExistsException.class})
    public ResponseEntity<?> handleImageExistsException(Exception ex){
        return new ResponseEntity<>(new ErrorResponse("RESOURCE_EXISTS", ex.getMessage()), HttpStatus.CONFLICT);
    }

    // Handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();

        // Extract all validation errors and collect their default messages
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                String errorMessage = error.getDefaultMessage();
                errorMessages.add(errorMessage);
            }
        });

        // Create a ValidationErrorResponse object with the collected error messages
        ValidationErrorResponse errorResponse = new ValidationErrorResponse("VALIDATION_ERROR", errorMessages);

        // Return the ValidationErrorResponse object with HTTP 400 Bad Request status
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    // Handle Authentication Exception and its subclasses
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse("INVALID_AUTHENTICATION", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}
