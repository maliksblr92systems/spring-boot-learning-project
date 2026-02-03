package com.evergreen.EvergreenOrchestratorServer.services.order;

import com.evergreen.EvergreenOrchestratorServer.dtos.requests.CreateOrderRequestDto;

public interface IOrderServiceOrchestrator {

    public void createOrder(CreateOrderRequestDto requestDto);

}
