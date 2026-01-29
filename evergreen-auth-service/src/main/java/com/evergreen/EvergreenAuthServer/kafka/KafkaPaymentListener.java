package com.evergreen.EvergreenAuthServer.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaPaymentListener {

    // @KafkaListener(topics = "payment-topic", containerFactory =
    // "kafkaCompleteRealtimeListenerContainer")
    @KafkaListener(
            // topics = "payment-topic",
            groupId = "auth-service", containerFactory = "kafkaListenerContainer",
            topicPartitions = {@TopicPartition(topic = "payment-topic", partitions = {"0"})})
    public void consumeEvents0(String data) {
        log.info("[KafkaPaymentListener] [consumeEvents0] [0] {}", data.toString());
    }

    @KafkaListener(
            // topics = "payment-topic",
            groupId = "auth-service", containerFactory = "kafkaListenerContainer",
            topicPartitions = {@TopicPartition(topic = "payment-topic", partitions = {"1"})})
    public void consumeEvents1(String data) {
        log.info("[KafkaPaymentListener] [consumeEvents1] [1] {}", data.toString());

    }

}
