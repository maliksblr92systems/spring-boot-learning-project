package com.evergreen.EvergreenPaymentServer.services.order;

import java.util.List;

import com.evergreen.EvergreenPaymentServer.dtos.order.CreateOrderRequestDto;
import com.evergreen.lib.dtos.order.OrderDto;

public interface IOrderService {

    public OrderDto create(CreateOrderRequestDto requestDto);

    public List<OrderDto> getAll();

    public OrderDto get(int id);
}
