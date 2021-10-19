package com.revature.dungeonsite.controllers;

import com.revature.dungeonsite.exceptions.ResourceNotFoundException;
import com.revature.dungeonsite.exceptions.UserNotFoundException;
import com.revature.dungeonsite.models.SiteUser;
import com.revature.dungeonsite.repositories.UserRepository;
import com.revature.dungeonsite.utils.KeyUtils;
import com.revature.dungeonsite.utils.PasswordUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository users;

    public UserController(UserRepository users) {
        this.users = users;
    }

    @GetMapping
    public ResponseEntity<List<SiteUser>> findAll() {
        return ResponseEntity.ok(this.users.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteUser> getUserByID(@PathVariable(value="id") Long userID)
            throws UserNotFoundException {
        SiteUser user = getNeoUser(userID);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<SiteUser> getUserByID(@PathVariable(value="username") Long username)
            throws UserNotFoundException {
        SiteUser user = getNeoUser(username);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteUser> updateUser(@PathVariable(value = "id") Long userID,
        @RequestBody SiteUser user) throws UserNotFoundException {
        SiteUser neoUser = getNeoUser(userID);
        if (user.getUsername() != null && !user.getUsername().equals(""))
            neoUser.setUsername(user.getUsername());
        if (user.getPassword() != null && !user.getPassword().equals("")
            && !user.getPassword().equals(neoUser.getPassword()) )
            neoUser.setPassword(PasswordUtils.encrypt(user.getPassword()) );
        if (user.getFirstName() != null && !user.getFirstName().equals(""))
            neoUser.setFirstName(user.getFirstName());
        if (user.getLastName() != null && !user.getLastName().equals(""))
            neoUser.setLastName(user.getLastName());
        if (user.getEmail() != null && !user.getEmail().equals(""))
            neoUser.setEmail(user.getEmail());

        return ResponseEntity.ok(this.users.save(neoUser));
    }

    private SiteUser getNeoUser(@PathVariable("id") Long userID) throws UserNotFoundException {
        return users.findById(userID)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found for ID: " + userID)
                );
    }

    @PostMapping
    public SiteUser makeUser(@RequestBody SiteUser neoUser) {
        neoUser.setUserID(KeyUtils.nextKey());
        neoUser.setPassword(PasswordUtils.encrypt(neoUser.getPassword()) );
        return this.users.save(neoUser);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userID)
            throws UserNotFoundException {
        SiteUser oldUser = getNeoUser(userID);
        this.users.delete(oldUser);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
