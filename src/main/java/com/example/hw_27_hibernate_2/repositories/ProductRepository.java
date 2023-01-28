package com.example.hw_27_hibernate_2.repositories;

import com.example.hw_27_hibernate_2.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
