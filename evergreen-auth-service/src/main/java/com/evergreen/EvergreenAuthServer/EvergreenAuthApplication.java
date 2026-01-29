package com.evergreen.EvergreenAuthServer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EvergreenAuthApplication {



    public static void main(String[] args) {
        SpringApplication.run(EvergreenAuthApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            for (int x = 0; x < 10; x++) {
                // kafkaTemplate.send("payment-topic", "Hy payment(" + x + ")");

            }
        };
    }

}
