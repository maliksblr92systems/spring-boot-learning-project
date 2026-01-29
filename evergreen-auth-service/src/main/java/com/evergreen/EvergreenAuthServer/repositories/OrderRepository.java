package com.evergreen.EvergreenAuthServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evergreen.EvergreenAuthServer.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
