package com.revature.springskeleton.controllers;

import com.revature.springskeleton.exceptions.ResourceNotFoundException;
import com.revature.springskeleton.models.SiteUser;
import com.revature.springskeleton.repositories.UserRepository;
import com.revature.springskeleton.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserRepository users;

    @GetMapping("/")
    public List<SiteUser> findAll() {
        return this.users.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteUser> getUserByID(@PathVariable(value="id") Long userID)
            throws ResourceNotFoundException {
        SiteUser user = users.findById(userID)
                .orElseThrow(
                        () -> new ResourceAccessException("Employee not found for ID: " + userID)
                );
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteUser> updateUser(@PathVariable(value = "id") Long userID,
        @RequestBody SiteUser user) throws ResourceNotFoundException {
        SiteUser neoUser = users.findById(userID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Employee not found for ID: " + userID)
                );
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

    @PostMapping("/")
    public SiteUser makeUser(@RequestBody SiteUser neoUser) {
        neoUser.setPassword(PasswordUtils.encrypt(neoUser.getPassword()) );
        return this.users.save(neoUser);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userID)
            throws ResourceNotFoundException {
        SiteUser oldUser = users.findById(userID)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Employee not found for ID: " + userID)
                );
        this.users.delete(oldUser);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
