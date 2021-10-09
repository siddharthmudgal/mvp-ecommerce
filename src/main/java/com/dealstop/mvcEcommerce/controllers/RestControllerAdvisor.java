package com.dealstop.mvcEcommerce.controllers;

import com.dealstop.mvcEcommerce.datatransferobject.ErrorDTO;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerAdvisor {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleGenericNotFoundException(Exception e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
