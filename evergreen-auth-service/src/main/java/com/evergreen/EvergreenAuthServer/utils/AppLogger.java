package com.evergreen.EvergreenAuthServer.utils;


import org.springframework.stereotype.Component;

@Component
public class AppLogger {


    public void log(String message){
        System.out.println("========================================");
        System.out.println(message);
        System.out.println("========================================");
    }
}
