package com.evergreen.EvergreenAuthServer.advices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.evergreen.lib.handlers.EvergreenExceptionHanlder;
import com.evergreen.lib.utils.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler extends EvergreenExceptionHanlder {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleBadCredentialsException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(ex.getMessage()));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(ex.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final List<String> errorsList = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + " " + error.getDefaultMessage()).toList();
        return ResponseEntity.unprocessableEntity().body(new ApiError(errorsList));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        final Throwable root = ex.getRootCause() == null ? ex : ex.getRootCause();
        final String error = root.getMessage().replace("ERROR", "").replace("Detail", "");
        return new ResponseEntity<>(new ApiError(error), HttpStatus.CONFLICT);
    }
}
