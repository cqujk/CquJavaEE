package org.exp4auth.controller;

import org.exp4auth.error.UserAlreadyExistsException;
import org.exp4auth.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
//        System.out.println("UserAlreadyExistsException caught: " + ex.getMessage());
//        ResponseEntity<String> response =ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
//        System.out.println("response: "+ response);
//        return response;
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

