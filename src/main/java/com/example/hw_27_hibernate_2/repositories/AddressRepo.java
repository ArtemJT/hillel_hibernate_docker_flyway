package com.example.hw_27_hibernate_2.repositories;

import com.example.hw_27_hibernate_2.entities.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends CrudRepository<Address, Integer> {

    @Query(value = "update store_db.address set country=?1, city=?2, street=?3, house=?4 where fk_client_id=?5 returning id",
            nativeQuery = true)
    Integer updateAddressForClient(String country, String city, String street, String house, Integer clientId);
}
