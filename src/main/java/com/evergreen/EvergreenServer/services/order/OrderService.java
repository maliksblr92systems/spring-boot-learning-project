package com.evergreen.EvergreenServer.services.order;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.evergreen.EvergreenServer.advices.ApiException;
import com.evergreen.EvergreenServer.dtos.entity.OrderDto;
import com.evergreen.EvergreenServer.dtos.requests.order.CreateOrderRequestDto;
import com.evergreen.EvergreenServer.mappers.OrderMapper;
import com.evergreen.EvergreenServer.models.AppUser;
import com.evergreen.EvergreenServer.models.Order;
import com.evergreen.EvergreenServer.models.Product;
import com.evergreen.EvergreenServer.repositories.AppUserRepository;
import com.evergreen.EvergreenServer.repositories.OrderRepository;
import com.evergreen.EvergreenServer.repositories.ProductRepository;
import com.evergreen.EvergreenServer.security.dtos.CustomUserDetail;
import com.evergreen.EvergreenServer.services.order.implementations.IOrderService;

@Service
public class OrderService implements IOrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AppUserRepository appUserRepository;


    public OrderService(ProductRepository productRepository, OrderRepository orderRepository, OrderMapper orderMapper,
            AppUserRepository appUserRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.appUserRepository = appUserRepository;
    }


    private void validateProductStock(Product product, int quantity) {

        if (product.getStock() < quantity) {
            throw ApiException.badRequest("Stock not available.");
        }

    }

    private void updateProductStock(Product product, int quantity) {
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }

    private Order createOrder(Product product, int quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userPrincipal = (CustomUserDetail) auth.getPrincipal();
        AppUser appUser = appUserRepository.findByEmail(userPrincipal.getUsername());
        if (appUser == null) {
            throw ApiException.badRequest("user not found.");
        }

        Order order = new Order();
        order.setAmount(quantity * product.getPrice());
        order.setProduct(product);
        order.setUser(appUser);
        order.setQuantity(quantity);

        return orderRepository.save(order);
    }


    // if a transaction already exists -> join
    // if transaction does not exist -> create
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDto create(CreateOrderRequestDto requestDto) {

        int productId = requestDto.getProductId();
        int quantity = requestDto.getQuantity();

        Product product = productRepository.findById(productId).orElseThrow(() -> {
            throw ApiException.notFound("product not found.");
        });

        validateProductStock(product, quantity);
        updateProductStock(product, quantity);
        if (quantity >= 1) {
            throw ApiException.badRequest("diliberatlly failed request.");

        }
        Order order = createOrder(product, quantity);
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
