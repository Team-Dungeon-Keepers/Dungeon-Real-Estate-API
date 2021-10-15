package com.revature.dungeonsite.repositories;

import com.revature.dungeonsite.models.GameLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLanguageRepository extends JpaRepository<GameLanguage, Long> {
    public GameLanguage findByGameID(Long gameID);
    public GameLanguage findByLanguageID(Long languageID);
}
