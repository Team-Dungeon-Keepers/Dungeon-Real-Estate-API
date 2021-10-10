package com.revature.springskeleton.repositories;

import com.revature.springskeleton.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    public Address findByStreet(String street);
    public Address findByCity(String city);
    public Address findByState(String state);
    public Address findByZip(Long zip);
}
