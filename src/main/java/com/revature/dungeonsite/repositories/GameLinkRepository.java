package com.revature.dungeonsite.repositories;

import com.revature.dungeonsite.models.GameLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLinkRepository extends JpaRepository<GameLink, Long> {
    public GameLink findByGameID(Long gameID);
    public GameLink findByLinkID(Long linkID);
}
