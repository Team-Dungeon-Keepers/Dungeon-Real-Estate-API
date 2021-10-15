package com.revature.dungeonsite.repositories;

import com.revature.dungeonsite.models.GameSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameScheduleRepository extends JpaRepository<GameSchedule, Long> {
    public GameSchedule findByGameID(Long gameID);
    public GameSchedule findByScheduleID(Long linkID);
}
