package com.evergreen.EvergreenAuthServer.kafka;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class KafkaUserActivityListener {

    // 4 means n-1 tries exactly three retries
    // exclude failure for RuntimeException.class
    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 700, maxDelay = 12000, multiplier = 3), exclude = {RuntimeException.class})
    @KafkaListener(topics = "user-activity-topic", containerFactory = "kafkaListenerContainer")

    public void consumer(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) int offset) {

    }

    @DltHandler
    public void dltHandler(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) int offset) {

    }

}
