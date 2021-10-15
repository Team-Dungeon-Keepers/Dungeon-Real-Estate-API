package com.revature.dungeonsite.repositories;

import com.revature.dungeonsite.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    public Game findByGameName(String gameName);
    public Game findByGameMasterID(Long gameMasterID);
    public Game findByRulesID(Long rulesID);
}
