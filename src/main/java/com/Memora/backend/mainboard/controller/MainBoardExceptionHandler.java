package com.Memora.backend.mainboard.controller;

import com.Memora.backend.mainboard.service.InvalidRawInputException;
import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class MainBoardExceptionHandler {

    @ExceptionHandler(InvalidRawInputException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidRawInput(InvalidRawInputException exception) {
        return ResponseEntity.badRequest().body(Map.of(
                "code", "INVALID_RAW_INPUT",
                "message", exception.getMessage(),
                "timestamp", Instant.now()
        ));
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            org.springframework.web.bind.MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Request is invalid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "code", "INVALID_REQUEST",
                "message", message,
                "timestamp", Instant.now()
        ));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleUploadSizeExceeded() {
        return ResponseEntity.badRequest().body(Map.of(
                "code", "INVALID_RAW_INPUT",
                "message", "Uploaded image exceeds the configured size limit",
                "timestamp", Instant.now()
        ));
    }
}
