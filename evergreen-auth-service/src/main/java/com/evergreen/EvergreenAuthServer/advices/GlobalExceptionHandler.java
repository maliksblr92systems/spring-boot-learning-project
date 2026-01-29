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

import com.evergreen.EvergreenAuthServer.utils.ApiError;

import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiErrorException(ApiException apiException) {
        HttpStatus httpStatus = apiException.getHttpStatus();
        String error = apiException.getApiError().getError();
        System.out.println("============================================");
        System.out.println(error);
        System.out.println("============================================");

        return new ResponseEntity<>(new ApiError(error), httpStatus);
    }



    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleBadCredentialsException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(ex.getMessage()));

    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(ex.getMessage()));

    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Unhandled exception occurred", ex);
        return ResponseEntity.internalServerError().body(new ApiError(ex.getMessage()));

    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(SignatureException ex) {
        log.error("Unhandled [SignatureException] occurred", ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(ex.getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorsList =
                ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + " " + error.getDefaultMessage()).toList();
        return ResponseEntity.unprocessableEntity().body(new ApiError(errorsList));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Throwable root = ex.getRootCause() == null ? ex : ex.getRootCause();
        String error = root.getMessage().replace("ERROR", "").replace("Detail", "");
        return new ResponseEntity<>(new ApiError(error), HttpStatus.CONFLICT);
    }
}

