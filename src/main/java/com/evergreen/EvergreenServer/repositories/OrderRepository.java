package com.evergreen.EvergreenServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.evergreen.EvergreenServer.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
