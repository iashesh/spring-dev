package com.binarray.spring.dev.security.data.repository;

import com.binarray.spring.dev.security.data.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
