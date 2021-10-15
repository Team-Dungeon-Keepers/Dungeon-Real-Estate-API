package com.revature.dungeonsite.repositories;

import com.revature.dungeonsite.models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    public UserAddress findByUserID(Long gameID);
    public UserAddress findByAddressID(Long addressID);
}
