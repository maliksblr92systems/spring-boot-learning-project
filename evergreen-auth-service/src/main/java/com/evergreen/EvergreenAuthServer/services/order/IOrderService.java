package com.evergreen.EvergreenAuthServer.services.order;

import java.util.List;

import com.evergreen.EvergreenAuthServer.dtos.entity.OrderDto;
import com.evergreen.EvergreenAuthServer.dtos.requests.order.CreateOrderRequestDto;

public interface IOrderService {

    public OrderDto create(CreateOrderRequestDto requestDto);

    public List<OrderDto> getAll();

    public OrderDto get(int id);
}
