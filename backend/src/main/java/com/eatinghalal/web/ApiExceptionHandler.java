package com.eatinghalal.web;

import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, String>> status(ResponseStatusException exception) {
    return ResponseEntity.status(exception.getStatusCode())
        .body(
            Map.of(
                "message",
                exception.getReason() == null ? "Request failed" : exception.getReason()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> validation() {
    return ResponseEntity.badRequest()
        .body(Map.of("message", "Quantity must be between 1 and 999 and a product is required."));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, String>> conflict() {
    return ResponseEntity.status(409)
        .body(Map.of("message", "This record already exists or conflicts with stored data."));
  }
}
