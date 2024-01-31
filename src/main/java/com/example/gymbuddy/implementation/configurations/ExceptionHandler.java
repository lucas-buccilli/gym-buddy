package com.example.gymbuddy.implementation.configurations;

import com.example.gymbuddy.infrastructure.exceptions.AlreadyExistsException;
import com.example.gymbuddy.infrastructure.exceptions.InvalidRequestException;
import com.example.gymbuddy.infrastructure.exceptions.MachineNotFoundException;
import com.example.gymbuddy.infrastructure.exceptions.MemberNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> validationExceptionHandler(InvalidRequestException e, HttpServletRequest request) {

        return new ResponseEntity<>(
                new ExceptionResponse(
                        HttpStatus.BAD_REQUEST,
                        "Invalid Request Exception",
                        e.getMessage(),
                        request
                ),
                HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedExceptionHandler(AccessDeniedException e, HttpServletRequest request) {

        return new ResponseEntity<>(
                new ExceptionResponse(
                        HttpStatus.FORBIDDEN,
                        "Access Denied",
                        e.getMessage(),
                        request
                ),
                HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<?> validationExceptionHandler(Exception e, HttpServletRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal Server Error",
                        e.getMessage(),
                        request
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> validationExceptionHandler(AlreadyExistsException e, HttpServletRequest request) {

        return new ResponseEntity<>(
                new ExceptionResponse(
                        HttpStatus.BAD_REQUEST,
                        "Already Exists Exception",
                        e.getMessage(),
                        request
                ),
                HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MachineNotFoundException.class)
    public ResponseEntity<?> machineNotFoundExceptionHandler(MachineNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        HttpStatus.NOT_FOUND,
                        "Machine Not Found",
                        e.getMessage(),
                        request
                ),
                HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> memberNotFoundExceptionHandler(MemberNotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        HttpStatus.NOT_FOUND,
                        "Member Not Found",
                        e.getMessage(),
                        request
                ),
                HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    public class ExceptionResponse {
       private final LocalDateTime timestamp;
       private final HttpStatus status;
       private final String error;
       private final String message;
       private final String path;

       public ExceptionResponse(HttpStatus status, String error, String message, HttpServletRequest request) {
           timestamp = LocalDateTime.now();
           this.status = status;
           this.error = error;
           this.message = message;
           this.path = request.getRequestURI();
       }
    }
}
