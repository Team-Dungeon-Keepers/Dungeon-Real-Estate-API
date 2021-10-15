package com.revature.dungeonsite.repositories;

import com.revature.dungeonsite.models.GameBehavior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameBehaviorRepository extends JpaRepository<GameBehavior, Long> {
    public GameBehavior findByGameID(Long gameID);
    public GameBehavior findByBehaviorID(Long behaviorID);
}
