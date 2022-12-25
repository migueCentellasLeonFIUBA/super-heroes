package com.ejercicio.mindata.superheroes.controller;

import com.ejercicio.mindata.superheroes.dto.ErrorDTO;
import com.ejercicio.mindata.superheroes.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDTO> httpRequestMethodNotSupportedExceptionHandler(){
        ErrorDTO error = ErrorDTO.builder().message("Metodo No Permitido").build();
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> dataIntegrityViolationExceptionHandler() {
        ErrorDTO error = ErrorDTO.builder().message("Faltan Atributos para actualizar").build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> methodArgumentTypeMismatchExceptionHandler() {
        ErrorDTO error = ErrorDTO.builder().message("El Id debe ser de valor numerico").build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
