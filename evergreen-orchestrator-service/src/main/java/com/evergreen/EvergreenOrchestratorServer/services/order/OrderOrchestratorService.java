package com.evergreen.EvergreenOrchestratorServer.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evergreen.EvergreenOrchestratorServer.dtos.requests.CreateOrderRequestDto;
import com.evergreen.EvergreenOrchestratorServer.grpc.client.GrpcProductServiceClient;

@Service
public class OrderOrchestratorService implements IOrderServiceOrchestrator {

    @Autowired
    private GrpcProductServiceClient grpcProductServiceClient;

    public void createOrder(CreateOrderRequestDto requestDto) {
        boolean isAvailable = grpcProductServiceClient.validateStock(requestDto.getProductId(), requestDto.getQuantity());
        System.out.println("=======================================");
        System.out.println("isAvailable => " + isAvailable);
        System.out.println("=======================================");

        // validate product
        // validate stock
        // reserve stock
        // create order
        // log order execution
        // initiate payment
    }

}
