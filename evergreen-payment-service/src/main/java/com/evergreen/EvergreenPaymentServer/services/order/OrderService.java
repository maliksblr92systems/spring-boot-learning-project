package com.evergreen.EvergreenAuthServer.services.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.evergreen.EvergreenAuthServer.constants.enums.UserActivityStatus;
import com.evergreen.EvergreenAuthServer.constants.enums.UserActivityType;
import com.evergreen.EvergreenAuthServer.dtos.requests.order.CreateOrderRequestDto;
import com.evergreen.EvergreenAuthServer.models.AppUserModel;
import com.evergreen.EvergreenAuthServer.models.Order;
import com.evergreen.EvergreenAuthServer.models.Product;
import com.evergreen.EvergreenAuthServer.repositories.AppUserRepository;
import com.evergreen.EvergreenAuthServer.repositories.OrderRepository;
import com.evergreen.EvergreenAuthServer.repositories.ProductRepository;
import com.evergreen.EvergreenAuthServer.security.dtos.CustomUserDetail;
import com.evergreen.EvergreenAuthServer.services.user_activity.IUserActivityService;
import com.evergreen.EvergreenPaymentServer.mappers.OrderMapper;
import com.evergreen.lib.dtos.order.OrderDto;
import com.evergreen.lib.utils.ApiException;

@Service
public class OrderService implements IOrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AppUserRepository appUserRepository;
    private final IUserActivityService userActivityService;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, OrderMapper orderMapper, AppUserRepository appUserRepository,
            @Qualifier("userActivityService") IUserActivityService userActivityService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.appUserRepository = appUserRepository;
        this.userActivityService = userActivityService;
    }

    private void validateProductStock(Product product, int quantity) {

        if (product.getStock() < quantity) {
            throw ApiException.badRequest("Insufficient stock.");
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void updateProductStock(Product product, int quantity) {
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Order createOrder(Product product, int quantity, AppUserModel user) {

        Order order = new Order();
        order.setAmount(quantity * product.getPrice());
        order.setProduct(product);
        order.setUser(user);
        order.setQuantity(quantity);
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userPrincipal = (CustomUserDetail) auth.getPrincipal();
        AppUserModel user = appUserRepository.findByEmail(userPrincipal.getUsername());
        if (user == null) {
            throw ApiException.badRequest("user not found.");
        }

        int productId = requestDto.getProductId();
        int quantity = requestDto.getQuantity();

        Product product = productRepository.findById(productId).orElseThrow(() -> {
            throw ApiException.notFound("product not found.");
        });
        Order order;
        try {
            validateProductStock(product, quantity);
            updateProductStock(product, quantity);
            order = createOrder(product, quantity, user);

            userActivityService.create(user, UserActivityType.ORDER, UserActivityStatus.SUCCESS);

        } catch (Exception ex) {
            userActivityService.create(user, UserActivityType.ORDER, UserActivityStatus.FAILURE);
            throw ApiException.badRequest(ex.getMessage());

        }
        return orderMapper.toDto(order);

    }

    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toDtoList(orders);
    }

    public OrderDto get(int id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            throw ApiException.notFound("order not found.");
        });
        return orderMapper.toDto(order);
    }

}
