package com.example.hw_27_hibernate_2.repositories;

import com.example.hw_27_hibernate_2.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends CrudRepository<OrderItem, Integer> {
}
