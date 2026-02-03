package com.evergreen.EvergreenOrchestratorServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EvergreenOrchestratorServerApplication {

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(EvergreenOrchestratorServerApplication.class, args);
    }

}
