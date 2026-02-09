package com.evergreen.EvergreenPaymentServer.services.order;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.evergreen.EvergreenPaymentServer.dtos.order.CreateOrderRequestDto;
import com.evergreen.EvergreenPaymentServer.mappers.OrderMapper;
import com.evergreen.EvergreenPaymentServer.models.OrderModel;
import com.evergreen.EvergreenPaymentServer.repositories.OrderRepository;
import com.evergreen.EvergreenPaymentServer.repositories.ProductRepository;
import com.evergreen.lib.Product;
import com.evergreen.lib.dtos.order.OrderDto;
import com.evergreen.lib.utils.ApiException;

import io.jsonwebtoken.lang.Arrays;

@Service
public class OrderService implements IOrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, OrderMapper orderMapper) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    // private void validateProductStock(Product product, int quantity) {

    //     if (product.getStock() < quantity) {
    //         throw ApiException.badRequest("Insufficient stock.");
    //     }

    // }

    // @Transactional(propagation = Propagation.REQUIRED)
    // private void updateProductStock(Product product, int quantity) {
    //     product.setStock(product.getStock() - quantity);
    //     productRepository.save(product);
    // }

    @Transactional(propagation = Propagation.REQUIRED)
    private OrderModel createOrder(Product product, int quantity) {

        OrderModel order = new OrderModel();
        // order.setAmount(quantity * product.getPrice());
        // order.setProduct(product);
        // order.setQuantity(quantity);
        if (quantity >= 1) {
            throw ApiException.badRequest("diliberatlly failed request.");

        }
        return orderRepository.save(order);
    }

    // @REQUIRED : if a transaction already exists -> join else if transaction does
    // not exist -> create
    // @REQUIRED_NEW : always create a new transaction , suspend previous if
    // @MANDATORY : requires a existing transaction else with throw an error

    // default config is (propagation = Propagation.REQUIRED, isolation =
    // Isolation.READ_COMMITTED)
    // isolation means how one transaction see the changes in other transactions

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto create(CreateOrderRequestDto requestDto) {

      return new OrderDto();

    }

    public List<OrderDto> getAll() {
        List<OrderModel> orders = orderRepository.findAll();
        // return orderMapper.toDtoList(orders);
         List<OrderDto> listDtos= List.of();
         return listDtos;
    }

    public OrderDto get(int id) {
        OrderModel order = orderRepository.findById(id).orElseThrow(() -> {
            throw ApiException.notFound("order not found.");
        });
        // return orderMapper.toDto(order);
        return new OrderDto();
    }

}
