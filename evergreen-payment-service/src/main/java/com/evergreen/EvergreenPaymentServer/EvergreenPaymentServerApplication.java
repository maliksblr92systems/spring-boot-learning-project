package com.evergreen.EvergreenPaymentServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EvergreenPaymentServerApplication {

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(EvergreenPaymentServerApplication.class, args);
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
