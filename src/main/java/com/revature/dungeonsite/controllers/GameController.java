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

import java.util.*;

@RestController
@CrossOrigin
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
	
	public GameController(
            AddressRepository nar,
            BehaviorRepository nbr,
            GameRepository games,
            GameAddressRepository ngar,
            GameBehaviorRepository ngb,
            GameLanguageRepository nglg,
            GameLinkRepository ngln,
            GameScheduleRepository gameSched,
            LanguageRepository nlr,
            LinkRepository nln,
            ScheduleRepository schedule,
            UserGameRepository userGames,
            UserRepository userRep) {

        this.ar = nar;
        this.br = nbr;
        this.gsr = gameSched;
        this.gar = ngar;
        this.gbr = ngb;
        this.glgr =nglg;
        this.glnr = ngln;
        this.lgr = nlr;
        this.lnr = nln;
        this.sr = schedule;
        this.ug = userGames;
        this.ur = userRep;
        this.games = games;
    }

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
    public ResponseEntity<GameFull> gameFullByID(@PathVariable(value="id") Long gameID)
            throws ResourceNotFoundException {
        GameFull returnThis = new GameFull();
        returnThis.setGame(this.getGameByGameID(gameID) );

        List<GameAddress> listGA = this.gar.findByGameID(gameID);
        List<GameBehavior> listGB = this.gbr.findByGameID(gameID);
        List<GameLanguage> listGLG = this.glgr.findByGameID(gameID);
        List<GameLink> listGLN = this.glnr.findByGameID(gameID);
        List<GameSchedule> listGS = this.gsr.findByGameID(gameID);

        List<Optional<Address>> listA = new ArrayList<>();
        List<Optional<Behavior>> listB = new ArrayList<>();
        List<Optional<Language>> listLG = new ArrayList<>();
        List<Optional<Link>> listLN = new ArrayList<>();
        List<Optional<Schedule>> listS = new ArrayList<>();

        for (GameAddress item: listGA) {
            listA.add(ar.findById(item.getAddressID()) );
        }
        for (GameBehavior item: listGB) {
            listB.add(br.findById(item.getBehaviorID()) );
        }
        for (GameLanguage item: listGLG) {
            listLG.add(lgr.findById(item.getLanguageID()) );
        }
        for (GameLink item: listGLN) {
            listLN.add(lnr.findById(item.getLinkID()) );
        }
        for (GameSchedule item: listGS) {
            listS.add(sr.findById(item.getScheduleID()) );
        }
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

    @PostMapping("/full")
    public ResponseEntity<GameFull> postGameFull(@RequestBody GameFull data) {
        Game tGame = data.getGame();
        List<Address> tAddress = data.getAddresses();
        List<Behavior> tBehavior = data.getBehaviors();
        List<Language> tLanguage = data.getLanguages();
        List<Link> tLink = data.getLinks();
        List<Schedule> tSchedule = data.getSchedules();

        GameFull response = new GameFull();
        response.setGame(makeGameIfNotExist(tGame));
        Long gameID = response.getGame().getGameID();
        System.out.println("GameID: "+ gameID);
        for (Address item : tAddress) {
            response.getAddresses().add(createAddressWithLink(item, gameID));
        }
        System.out.println("Addresses set");
        for (Behavior item : tBehavior) {
            response.getBehaviors().add(createBehaviorWithLink(item, gameID));
        }
        System.out.println("Behaviors set");
        for (Language item : tLanguage) {
            response.getLanguages().add(createLanguageWithLink(item, gameID));
        }
        System.out.println("Languages set");
        for (Link item : tLink) {
            response.getLinks().add(createLinkWithLink(item, gameID));
        }
        System.out.println("Links set");
        for (Schedule item : tSchedule) {
            response.getSchedules().add(createScheduleWithLink(item, gameID));
        }
        System.out.println("Schedules set");

        return ResponseEntity.ok(response);
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

    public Boolean containsGA(Long gameID, Long addressID) {
        List<GameAddress> list = this.gar.findByAddressID(addressID);

        for (GameAddress ga : list) {
            if (ga.getGameID() == gameID)
                return true;
        }

        return false;
    }

    public Boolean containsGB(Long gameID, Long behaviorID) {
        List<GameBehavior> list = this.gbr.findByBehaviorID(behaviorID);

        for (GameBehavior item : list) {
            if (item.getGameID() == gameID)
                return true;
        }

        return false;
    }

    public Boolean containsGLG(Long gameID, Long langID) {
        List<GameLanguage> list = this.glgr.findByLanguageID(langID);

        for (GameLanguage item : list) {
            if (item.getGameID() == gameID)
                return true;
        }

        return false;
    }

    public Boolean containsGLN(Long gameID, Long linkID) {
        List<GameLink> list = this.glnr.findByLinkID(linkID);

        for (GameLink item : list) {
            if (item.getGameID() == gameID)
                return true;
        }

        return false;
    }

    public Boolean containsGS(Long gameID, Long scheduleID) {
        List<GameSchedule> list = this.gsr.findByScheduleID(scheduleID);

        for (GameSchedule item : list) {
            if (item.getGameID() == gameID)
                return true;
        }

        return false;
    }

    private Address createAddressWithLink(Address add, Long gameID) {
        Address returnThis = null;

        System.out.println("In function");
        try {
            if (!ar.findById(add.getAddressID()).isPresent()) {
                returnThis = ar.save(add);
            } else {
                returnThis = add;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        System.out.println("Address done, making game_address");
        try {
            if (!containsGA(gameID, add.getAddressID())) {
                System.out.println("Making new address_game");
                gar.save(createGameAddress(gameID, add.getAddressID()));
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        System.out.println("Game_address successful");

        return (returnThis != null)?returnThis:add;
    }

    private Behavior createBehaviorWithLink(Behavior behave, Long gameID) {
        Behavior returnThis = null;
        try {
            if (!br.findById(behave.getBehaviorID()).isPresent())
                returnThis = br.save(behave);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            if (!containsGB(gameID, behave.getBehaviorID()))
                gbr.save(createGameBehavior(gameID, behave.getBehaviorID()));
        } catch (Exception e) {
            //e.printStackTrace();
        }


        return (returnThis != null)?returnThis:behave;
    }

    private GameAddress createGameAddress(Long gi, Long ai) {
        GameAddress returnThis = new GameAddress(KeyUtils.nextKey(), gi, ai);
        return returnThis;
    }

    private GameBehavior createGameBehavior(Long gi, Long bi) {
        GameBehavior returnThis = new GameBehavior(KeyUtils.nextKey(), gi, bi);
        return returnThis;
    }

    private GameLanguage createGameLanguage(Long gi, Long lgi) {
        GameLanguage returnThis = new GameLanguage(KeyUtils.nextKey(), gi, lgi);
        return returnThis;
    }

    private GameLink createGameLink(Long gi, Long lni) {
        GameLink returnThis = new GameLink(KeyUtils.nextKey(), gi, lni);
        return returnThis;
    }

    private GameSchedule createGameSchedule(Long gi, Long si) {
        GameSchedule returnThis = new GameSchedule(KeyUtils.nextKey(), gi, si);
        return returnThis;
    }

    private Game makeGameIfNotExist(Game tGame) {
        tGame.setGameID(KeyUtils.nextKey());
        try {
            if (!games.findById(tGame.getGameID()).isPresent())
                return this.games.save(tGame);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return tGame;
    }

    private Language createLanguageWithLink(Language lang, Long gameID) {
        Language returnThis = null;
        try {
            if (!lgr.findById(lang.getLanguageid()).isPresent())
                returnThis = lgr.save(lang);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            if(lang != null)
                if (!containsGLG(gameID, lang.getLanguageid()))
                    glgr.save(createGameLanguage(gameID, lang.getLanguageid()));
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return (returnThis != null)?returnThis:lang;
    }

    private Link createLinkWithLink(Link link, Long gameID) {
        Link returnThis = null;
        try {
            if (!lnr.findById(link.getLinkid()).isPresent())
                returnThis = lnr.save(link);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            if (link != null)
                if (!containsGLN(gameID, link.getLinkid()) )
                    glnr.save(createGameLink(gameID, link.getLinkid()) );
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return (returnThis != null)?returnThis:link;
    }

    private Schedule createScheduleWithLink(Schedule schedule, Long gameID) {
        Schedule returnThis = null;
        try {
            if (!sr.findById(schedule.getScheduleID()).isPresent())
                returnThis = sr.save(schedule);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            if (schedule != null)
                if (!containsGS(gameID, schedule.getScheduleID()))
                    gsr.save(createGameSchedule(gameID, schedule.getScheduleID()));
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return (returnThis != null)?returnThis:schedule;
    }

}


