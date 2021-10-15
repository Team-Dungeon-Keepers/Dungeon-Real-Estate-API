package com.revature.dungeonsite.controllers;

import com.revature.dungeonsite.exceptions.ResourceNotFoundException;
import com.revature.dungeonsite.models.Game;
import com.revature.dungeonsite.repositories.GameRepository;
import com.revature.dungeonsite.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//possibly change mapping later?
@RestController
@CrossOrigin
@RequestMapping("/api/games")
public class GameController {
	
    private GameRepository games;
	
	public GameController(GameRepository games) {
        this.games = games;
    }

    private ResponseEntity<Game> getGameByGameID(Long gameID) throws ResourceNotFoundException {
        return games.findById(gameID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Game not found for ID: " + gameID)
                );
    }
    private Game getNeoGame(@PathVariable("id") Long gameID) throws ResourceNotFoundException {
        return games.findById(gameID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Game not found for ID: " + gameID)
                );
    }
    @GetMapping
    public ResponseEntity<List<Game>> findAll() {
        return this.games.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameByID(@PathVariable(value="id") Long gameID)
            throws ResourceNotFoundException {
        Game game = getGameByGameID(gameID);
        return ResponseEntity.ok().body(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable(value = "id") Long gameID,
        @RequestBody Game game) throws ResourceNotFoundException {
        Game neoGame = getGameByGameID(gameID);
        if (game.getGameName() != null && !game.getGameName().equals(""))
            neoGame.setGameName(game.getGameName());
        if (game.getGamePassword() != null && !game.getGamePassword().equals("") && neoGame.getPassword()!=null && !game.getGamePassword().equals(neoGame.getPassword()))
            neoGame.setGamePassword(game.getGamePassword());
        if (game.getRulesID() != null)
            neoGame.setRulesID(game.getRulesID());
        if (game.getLanguageID() != null)
            neoGame.setZip(game.getLanguageID());
        if (game.getDescription() != null && !game.getDescription().equals(""))
            neoGame.setDescription(game.getDescription());
        return ResponseEntity.ok(this.games.save(neoGame));
    }

    @PostMapping
    public Game makeGame(@RequestBody Game neoGame) {
		neoGame.setGameID(KeyUtils.nextKey());
		if(neoGame.getPassword()!=null && !neoGame.getPassword().equals(""))
			neoGame.setPassword(PasswordUtils.encrypt(neoGame.getPassword()));
        return this.games.save(neoGame);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteGame(@PathVariable(value = "id") Long gameID)
            throws ResourceNotFoundException {
        Game oldGame = getGameByGameID(gameID);
        this.games.delete(oldGame);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
