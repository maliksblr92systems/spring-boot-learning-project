package com.evergreen.EvergreenServer.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.evergreen.EvergreenServer.dtos.entity.OrderDto;
import com.evergreen.EvergreenServer.dtos.requests.order.CreateOrderRequestDto;
import com.evergreen.EvergreenServer.services.order.implementations.IOrderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final IOrderService orderService;


    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public ResponseEntity<OrderDto> create(@Valid @RequestBody CreateOrderRequestDto requestDto) {
        return new ResponseEntity<>(this.orderService.create(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<OrderDto>> getAll() {
        return new ResponseEntity<>(this.orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(this.orderService.get(id), HttpStatus.OK);
    }

}
