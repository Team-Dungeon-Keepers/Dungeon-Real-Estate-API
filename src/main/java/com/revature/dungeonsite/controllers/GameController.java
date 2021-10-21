package com.revature.dungeonsite.controllers;

import com.revature.dungeonsite.exceptions.ResourceNotFoundException;
import com.revature.dungeonsite.models.*;
import com.revature.dungeonsite.repositories.*;
import com.revature.dungeonsite.utils.KeyUtils;
import com.revature.dungeonsite.utils.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;

@RestController
@CrossOrigin
@NoArgsConstructor  @AllArgsConstructor
@RequestMapping("/api/games")
public class GameController {

    private AddressRepository ar;
    private BehaviorRepository br;
    private GameRepository games;
    private GameAddressRepository gar;
    private GameBehaviorRepository gbr;
    private GameLanguageRepository glgr;
    private GameLinkRepository glnr;
    private GameScheduleRepository gsr;
    private LanguageRepository lgr;
    private LinkRepository lnr;
    private ScheduleRepository sr;
    private UserGameRepository ug;
    private UserRepository ur;
	
//	public GameController(
//            AddressRepository nar,
//            BehaviorRepository nbr,
//            GameRepository games,
//            GameAddressRepository ngar,
//            GameScheduleRepository gameSched,
//            ScheduleRepository schedule,
//            UserGameRepository userGames,
//            UserRepository userRep) {
//
//        this.ar = nar;
//        this.br = nbr;
//        this.gsr = gameSched;
//        this.gar = ngar;
//        this.sr = schedule;
//        this.ug = userGames;
//        this.ur = userRep;
//        this.games = games;
//    }

    public Game getGameByGameID(Long gameID) throws ResourceNotFoundException {
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

    @GetMapping("/address/{id}")
    public ResponseEntity<List<Optional<Address>>> getGameAddressesByID(@PathVariable(value="id") Long gameID) {
        List<GameAddress> listGA = gar.findByGameID(gameID);
        List<Optional<Address>> list = new ArrayList<>();

        for (GameAddress item: listGA) {
            list.add(ar.findById(item.getAddressID()) );
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/full/{id}")
    public ResponseEntity<GameFull> gameFullByID(@PathVariable(value="id") Long gameID) {
        GameFull returnThis = new GameFull();
        returnThis.setGame(games.findById(gameID) );

//        List<GameAddress> listGA = this.gar.findByGameID(gameID);
//        List<GameBehavior> listGB = this.gbr.findByGameID(gameID);
//        List<GameLanguage> listGLG = this.glgr.findByGameID(gameID);
//        List<GameLink> listGLN = this.glnr.findByGameID(gameID);
//        List<GameSchedule> listGS = this.gsr.findByGameID(gameID);

        List<Optional<Address>> listA = new ArrayList<>();
        List<Optional<Behavior>> listB = new ArrayList<>();
        List<Optional<Language>> listLG = new ArrayList<>();
        List<Optional<Link>> listLN = new ArrayList<>();
        List<Optional<Schedule>> listS = new ArrayList<>();

//        for (GameAddress item: listGA) {
//            listA.add(ar.findById(item.getAddressID()) );
//        }
//        for (GameBehavior item: listGB) {
//            listB.add(br.findById(item.getBehaviorID()) );
//        }
//        for (GameLanguage item: listGLG) {
//            listLG.add(lgr.findById(item.getLanguageID()) );
//        }
//        for (GameLink item: listGLN) {
//            listLN.add(lnr.findById(item.getLinkID()) );
//        }
//        for (GameSchedule item: listGS) {
//            listS.add(sr.findById(item.getScheduleID()) );
//        }
        returnThis.setAddress(listA);
        returnThis.setBehavior(listB);
        returnThis.setLanguage(listLG);
        returnThis.setLink(listLN);
        returnThis.setSchedule(listS);

        return ResponseEntity.ok(returnThis);
    }

    @GetMapping("/master/{id}")
    public ResponseEntity<List<Game>> getGamesByMasterID(@PathVariable(value="id") Long masterID){
        List<Game> gamesFound = games.findByGameMasterID(masterID);

        return ResponseEntity.ok().body(gamesFound);
    }

    @GetMapping("/mastername/{namae}")
    public ResponseEntity<List<Game>>  findGamesByMasterName(@PathVariable(value="namae") String masterName) {
        SiteUser master = ur.findByUsername(masterName);
        List<Game> list = games.findByGameMasterID(master.getUserID());

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/name/{namae}")
    public ResponseEntity<Game> getGameByGameName(@PathVariable(value="namae") String name)
            throws ResourceNotFoundException {
        Game game = games.findByGameName(name);

        return ResponseEntity.ok().body(game);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Game>> getGamesByUserID(@PathVariable(value="id") Long ID) throws ResourceNotFoundException {
        List<UserGame> listUG = ug.findByUserID(ID);
        List<Game> list = new ArrayList<>();

        for (UserGame ug: listUG) {
            list.add(getGameByGameID(ug.getGameID()) );
        }

        return ResponseEntity.ok(list);
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

        UserGame neoUG = new UserGame();
        neoUG.setGameID(KeyUtils.nextKey());
        neoUG.setUserID(neoGame.getGameMasterID());
        neoUG.setGameID(neoGame.getGameID());

        this.ug.save(neoUG);
        return this.games.save(neoGame);
    }

    @PostMapping("/schedule/{id}")
    public Schedule makeGameSchedule(
            @PathVariable(value="id") Long gameID,
            @RequestBody Schedule gs) {

        gs.setScheduleID(KeyUtils.nextKey());
        Schedule linkThis = sr.save(gs);

        GameSchedule tempGS = new GameSchedule();
        tempGS.setID(KeyUtils.nextKey());
        tempGS.setScheduleID(linkThis.getScheduleID());
        tempGS.setGameID(gameID);
        this.gsr.save(tempGS);

        return linkThis;
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
