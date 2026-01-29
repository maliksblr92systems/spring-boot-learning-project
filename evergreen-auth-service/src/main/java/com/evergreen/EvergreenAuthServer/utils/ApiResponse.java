package com.evergreen.EvergreenAuthServer.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;

    public ApiResponse(T data) {
        this.data = data;

    }

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;

    }

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>(data, "success");
        return apiResponse;
    }

    public static <T> ApiResponse<T> created(T data, String entityName) {
        ApiResponse<T> apiResponse = new ApiResponse<>(data, entityName + " created successfully.");
        return apiResponse;
    }



}
