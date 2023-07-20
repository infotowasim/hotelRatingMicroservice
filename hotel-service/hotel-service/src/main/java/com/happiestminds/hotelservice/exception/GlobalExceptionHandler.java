package com.happiestminds.hotelservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Objects>> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        Map map = new HashMap<>();
        map.put("message",resourceNotFoundException.getMessage());
        map.put("success",false);
        map.put("status", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
    }
}
