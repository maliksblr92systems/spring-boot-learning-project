package com.evergreen.lib.utils;

import java.util.Objects;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiException extends RuntimeException {
    private final ApiError apiError;
    private HttpStatus httpStatus;

    public ApiException(ApiError apiError) {
        super(apiError.getError());
        this.apiError = apiError;
    }

    public ApiException(ApiError apiError, HttpStatus httpStatus) {
        super(apiError.getError());
        this.apiError = apiError;
        this.httpStatus = httpStatus;
    }

    public ApiError getApiError() {
        return apiError;
    }

    public static ApiException badRequest(String error) {
        ApiError apiError = new ApiError(error);
        return new ApiException(apiError, HttpStatus.BAD_REQUEST);
    }

    public static ApiException unAuthenticated(String error) {
        // error = Objects.equals(error, "") || error == null ? error : "UN_AUTHORIZED";
        ApiError apiError = new ApiError(error);
        return new ApiException(apiError, HttpStatus.UNAUTHORIZED);
    }

    public static ApiException notFound(String error) {
        error = Objects.equals(error, "") || error == null ? "NOT_FOUND" : error;
        ApiError apiError = new ApiError(error);
        return new ApiException(apiError, HttpStatus.NOT_FOUND);
    }

    public static ApiException internalServerError(String error) {
        error = Objects.equals(error, "") || error == null ? "INTERNAL_SERVER_ERROR" : error;
        ApiError apiError = new ApiError(error);
        return new ApiException(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
