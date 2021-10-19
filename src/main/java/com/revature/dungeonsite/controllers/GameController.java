package com.revature.dungeonsite.controllers;

import com.revature.dungeonsite.exceptions.ResourceNotFoundException;
import com.revature.dungeonsite.models.Game;
import com.revature.dungeonsite.repositories.GameRepository;
import com.revature.dungeonsite.utils.KeyUtils;
import com.revature.dungeonsite.utils.PasswordUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/games")
public class GameController {
	
    private GameRepository games;
	
	public GameController(GameRepository games) {
        this.games = games;
    }

    private Game getGameByGameID(Long gameID) throws ResourceNotFoundException {
        return games.findById(gameID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Game not found for ID: " + gameID)
                );
    }

    @GetMapping
    public ResponseEntity<List<Game>> findAll() {
        return ResponseEntity.ok(this.games.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameByID(@PathVariable(value="id") Long gameID)
            throws ResourceNotFoundException {
        Game game = getGameByGameID(gameID);
        return ResponseEntity.ok().body(game);
    }

    @GetMapping("/master/{id}")
    public ResponseEntity<List<Game>> getGamesByMasterID(@PathVariable(value="id") Long masterID){
        List<Game> gamesFound = (List<Game>) games.findByGameMasterID(masterID);

        return ResponseEntity.ok().body(gamesFound);
    }


    @GetMapping("/name/{namae}")
    public ResponseEntity<Game> getGameByGameName(@PathVariable(value="namae") String name)
            throws ResourceNotFoundException {
        Game game = games.findByGameName(name);

        return ResponseEntity.ok().body(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable(value = "id") Long gameID,
        @RequestBody Game game) throws ResourceNotFoundException {
        Game neoGame = getGameByGameID(gameID);
        if (game.getGameName() != null && !game.getGameName().equals(""))
            neoGame.setGameName(game.getGameName());
        if (game.getGamePassword() != null && !game.getGamePassword().equals("") && neoGame.getGamePassword()!=null && !game.getGamePassword().equals(neoGame.getGamePassword()))
            neoGame.setGamePassword(game.getGamePassword());
        if (game.getRulesID() != null)
            neoGame.setRulesID(game.getRulesID());
        if (game.getDescription() != null && !game.getDescription().equals(""))
            neoGame.setDescription(game.getDescription());
        return ResponseEntity.ok(this.games.save(neoGame));
    }

    @PostMapping
    public Game makeGame(@RequestBody Game neoGame) {
		neoGame.setGameID(KeyUtils.nextKey());
		if(neoGame.getGamePassword()!=null && !neoGame.getGamePassword().equals(""))
			neoGame.setGamePassword(PasswordUtils.encrypt(neoGame.getGamePassword()));
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
