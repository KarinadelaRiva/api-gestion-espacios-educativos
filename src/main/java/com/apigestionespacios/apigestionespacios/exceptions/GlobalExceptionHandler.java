package com.apigestionespacios.apigestionespacios.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> manejarNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarGenericException(Exception ex) {
        return new ResponseEntity<>("Error inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
