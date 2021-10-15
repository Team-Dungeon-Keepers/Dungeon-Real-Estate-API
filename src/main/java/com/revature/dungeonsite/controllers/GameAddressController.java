package com.revature.dungeonsite.controllers;

import com.revature.dungeonsite.exceptions.ResourceNotFoundException;
import com.revature.dungeonsite.models.GameAddress;
import com.revature.dungeonsite.repositories.GameAddressRepository;
//import com.revature.dungeonsite.utils.PasswordUtils;
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
@RequestMapping("/api/gameaddresses")
public class GameAddressController {
    private GameAddressRepository gameAddresses;
	
    public GameAddressController(GameAddressRepository gameAddresses) {
        this.gameAddresses = gameAddresses;
    }

    private ResponseEntity<GameAddress> getGameAddressByID(Long ID) throws ResourceNotFoundException {
        return gameAddresses.findById(ID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("GameAddress not found for ID: " + ID)
                );
    }
	private GameAddress getNeoGameAddress(@PathVariable("id") Long ID) throws ResourceNotFoundException {
        return addresses.findById(addressID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("GameAddress not found for ID: " + ID)
                );
    }
    @GetMapping
    public ResponseEntity<List<GameAddress>> findAll() {
        return this.gameAddresses.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameAddress> getGameAddressByID(@PathVariable(value="id") Long ID)
            throws ResourceNotFoundException {
        GameAddress gameAddress = getGameAddressByID(ID);
        return ResponseEntity.ok().body(gameAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameAddress> updateGameAddress(@PathVariable(value = "id") Long ID,
        @RequestBody GameAddress gameAddress) throws ResourceNotFoundException {
        GameAddress neoGameAddress = getGameAddressByID(ID);
        if (gameAddress.getGameID() != null)
            neoGameAddress.setGameID(gameAddress.getGameID());
        if (gameAddress.getAddressID() != null)
            neoGameAddress.setAddressID(gameAddress.getAddressID());
        return ResponseEntity.ok(this.gameAddresses.save(neoGameAddress));
    }

    @PostMapping
    public GameAddress makeGameAddress(@RequestBody GameAddress neoGameAddress) {
		neoGameAddress.setID(KeyUtils.nextKey());
        return this.gameAddresses.save(neoGameAddress);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteGameAddress(@PathVariable(value = "id") Long ID)
            throws ResourceNotFoundException {
        GameAddress oldGameAddress = getGameAddressByID(ID);
        this.gameAddresses.delete(oldGameAddress);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
