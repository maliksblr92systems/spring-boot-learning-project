package com.evergreen.lib.mappers;

import org.springframework.http.HttpStatus;

import io.grpc.Status;

public class HttpStatusAndGrpcStatusMapper {

    public static Status toGrpc(HttpStatus httpStatus) {
        return switch (httpStatus) {
            case BAD_REQUEST -> Status.INVALID_ARGUMENT;
            case UNAUTHORIZED -> Status.UNAUTHENTICATED;
            case FORBIDDEN -> Status.PERMISSION_DENIED;
            case NOT_FOUND -> Status.NOT_FOUND;
            case CONFLICT -> Status.ALREADY_EXISTS;
            case TOO_MANY_REQUESTS -> Status.RESOURCE_EXHAUSTED;
            case SERVICE_UNAVAILABLE -> Status.UNAVAILABLE;
            case GATEWAY_TIMEOUT -> Status.DEADLINE_EXCEEDED;
            default -> Status.INTERNAL;
        };
    }

    public static HttpStatus toHttp(Status status) {
        return switch (status.getCode()) {
            case INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST;
            case UNAUTHENTICATED -> HttpStatus.UNAUTHORIZED;
            case PERMISSION_DENIED -> HttpStatus.FORBIDDEN;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case RESOURCE_EXHAUSTED -> HttpStatus.TOO_MANY_REQUESTS;
            case UNAVAILABLE -> HttpStatus.SERVICE_UNAVAILABLE;
            case DEADLINE_EXCEEDED -> HttpStatus.GATEWAY_TIMEOUT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
