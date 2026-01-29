package com.evergreen.EvergreenAuthServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evergreen.EvergreenAuthServer.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
