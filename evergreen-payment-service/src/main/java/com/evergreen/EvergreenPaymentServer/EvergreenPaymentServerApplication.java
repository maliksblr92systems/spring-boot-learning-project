package com.evergreen.EvergreenPaymentServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EvergreenPaymentServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvergreenPaymentServerApplication.class, args);
    }

}
