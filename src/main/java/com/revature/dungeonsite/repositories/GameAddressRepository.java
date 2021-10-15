package com.revature.dungeonsite.repositories;

import com.revature.dungeonsite.models.GameAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameAddressRepository extends JpaRepository<GameAddress, Long> {
    public GameAddress findByGameID(Long gameID);
    public GameAddress findByAddressID(Long addressID);
}
