package com.evergreen.EvergreenPaymentServer.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evergreen.EvergreenPaymentServer.models.OrderModel;


@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Integer> {

}
