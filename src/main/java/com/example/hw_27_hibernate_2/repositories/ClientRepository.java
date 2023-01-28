package com.example.hw_27_hibernate_2.repositories;

import com.example.hw_27_hibernate_2.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
}
