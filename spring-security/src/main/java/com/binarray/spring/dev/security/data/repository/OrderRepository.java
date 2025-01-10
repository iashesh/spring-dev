package com.binarray.spring.dev.security.data.repository;

import com.binarray.spring.dev.security.data.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findAllByCustomerId(long customerId);
}
