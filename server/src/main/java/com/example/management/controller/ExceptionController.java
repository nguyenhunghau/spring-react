package com.example.management.controller;

import com.example.management.exception.NullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Nguyen Hung Hau
 * @email nguyenhunghau@fabercompany.co.jp
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = NullException.class)
    public ResponseEntity<Object> exception(NullException exception) {
        return new ResponseEntity<>("This object is null", HttpStatus.NOT_FOUND);
    }
}
