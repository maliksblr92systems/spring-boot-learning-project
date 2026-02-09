package com.evergreen.EvergreenPaymentServer.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaPaymentListener {

    @KafkaListener(topics = "payment-topic", groupId = "payment-service", containerFactory = "kafkaListenerContainer")
    public void listen(String data) {
        System.out.println("payment service listen to topic 'payment-topic' => " + data);
    }

}
