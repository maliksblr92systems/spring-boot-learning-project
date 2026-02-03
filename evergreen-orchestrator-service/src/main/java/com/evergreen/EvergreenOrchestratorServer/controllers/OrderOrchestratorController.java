package com.evergreen.EvergreenOrchestratorServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evergreen.EvergreenOrchestratorServer.dtos.requests.CreateOrderRequestDto;
import com.evergreen.EvergreenOrchestratorServer.services.order.IOrderServiceOrchestrator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/order")
public class OrderOrchestratorController {

    @Autowired
    @Qualifier("orderOrchestratorService")
    private IOrderServiceOrchestrator orderServiceOrchestrator;

    @PostMapping("")
    public ResponseEntity createOrder(@RequestBody @Valid CreateOrderRequestDto requestDto) {
        orderServiceOrchestrator.createOrder(requestDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);

    }
}
