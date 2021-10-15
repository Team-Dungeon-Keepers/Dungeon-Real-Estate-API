package com.revature.dungeonsite.repositories;

import com.revature.dungeonsite.models.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGameRepository extends JpaRepository<UserGame, Long> {
    public UserGame findByUserID(Long userID);
    public UserGame findByGameID(Long gameID);
}
