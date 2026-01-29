package com.evergreen.EvergreenAuthServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/events")
@Slf4j
public class EventController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("")
    public ResponseEntity<String> publish() {
        for (int x = 0; x < 10000; x++) {
            String message = "Hy " + x;
            kafkaTemplate.send("payment-topic", message);
        }
        return new ResponseEntity<>("Published successfully", HttpStatus.OK);
    }


}
