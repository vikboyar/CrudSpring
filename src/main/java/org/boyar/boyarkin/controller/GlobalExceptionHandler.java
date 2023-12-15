package org.boyar.boyarkin.controller;

import lombok.extern.slf4j.Slf4j;
import org.boyar.boyarkin.exception.*;
import org.boyar.boyarkin.utils.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getError()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchUserConflictException(UserConflictException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), e.getError()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchConstraintViolationException(DbConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(), e.getError()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchRusphoneViolationException(RusphoneViolationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_ACCEPTABLE.value(), e.getError()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchUnexpectedException(UnexpectedException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.GONE.value(), e.getError()), HttpStatus.GONE);
    }
}
