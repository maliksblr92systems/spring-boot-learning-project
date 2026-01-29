package com.evergreen.EvergreenAuthServer.utils;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {


    private String error;
    private List<String> errors;

    public ApiError(String error) {
        this.error = error;
    }


    public ApiError(List<String> errors) {
        this.errors = errors;
    }

}
