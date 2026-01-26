package com.evergreen.EvergreenServer.services.order;

import java.util.List;
import com.evergreen.EvergreenServer.dtos.entity.OrderDto;
import com.evergreen.EvergreenServer.dtos.requests.order.CreateOrderRequestDto;

public interface IOrderService {

    public OrderDto create(CreateOrderRequestDto requestDto);

    public List<OrderDto> getAll();

    public OrderDto get(int id);
}
