package com.apigestionespacios.apigestionespacios.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // lanzado para excepciones genericas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarGenericException(Exception ex) {
        return new ResponseEntity<>("Error inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // lanzado cuando no se encuentra un recurso al que se quiere acceder
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> manejarNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // lanzado cuando la solicitud no cumple con las reglas de negocio o valiadciones logicas
    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<String> manejarEntityValidationException(EntityValidationException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    // lanzado cuando se intenta crear un recurso que ya existe (ejemplo: crear una asignatura con un código que ya existe)
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<String> manejarResourceConflictException(ResourceConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }





}
