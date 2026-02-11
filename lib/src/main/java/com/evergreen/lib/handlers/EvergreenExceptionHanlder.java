package com.evergreen.lib.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.evergreen.lib.utils.ApiError;
import com.evergreen.lib.utils.ApiException;

import io.jsonwebtoken.security.SignatureException;

public class EvergreenExceptionHanlder {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiErrorException(ApiException apiException) {
        final HttpStatus httpStatus = apiException.getHttpStatus();
        final String error = apiException.getApiError().getError();
        System.out.println("============================================");
        System.out.println(error);
        System.out.println("============================================");

        return new ResponseEntity<>(new ApiError(error), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.internalServerError().body(new ApiError(ex.getMessage()));

    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(SignatureException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError(ex.getMessage()));

    }

}
