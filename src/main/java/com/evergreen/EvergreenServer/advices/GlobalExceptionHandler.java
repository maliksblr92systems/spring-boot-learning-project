package com.evergreen.EvergreenServer.advices;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.evergreen.EvergreenServer.utils.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiErrorException(ApiException apiException) {
        HttpStatus httpStatus = apiException.getApiError().getHttpStatus();
        String message = apiException.getApiError().getMessage();
        int status = apiException.getApiError().getHttpCode();
        return new ResponseEntity<>(new ApiError(message, httpStatus), httpStatus);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        System.out.println("====================================");
        System.out.println("ex" + ex.getClass().getName());
        System.out.println("====================================");
        return new ResponseEntity<>(new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorsList =
                ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + " " + error.getDefaultMessage()).toList();
        return new ResponseEntity<>(new ApiError(errorsList, HttpStatus.UNPROCESSABLE_ENTITY), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Throwable root = ex.getRootCause() == null ? ex : ex.getRootCause();
        String message = root.getMessage().replace("ERROR", "").replace("Detail", "");
        return new ResponseEntity<>(new ApiError(message, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }
}

