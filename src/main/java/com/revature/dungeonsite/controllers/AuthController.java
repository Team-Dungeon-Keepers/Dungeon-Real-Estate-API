package com.revature.dungeonsite.controllers;

import com.revature.dungeonsite.exceptions.UserNotFoundException;
import com.revature.dungeonsite.models.LoginResponse;
import com.revature.dungeonsite.models.SiteUser;
import com.revature.dungeonsite.repositories.UserRepository;
import com.revature.dungeonsite.utils.KeyUtils;
import com.revature.dungeonsite.utils.PasswordUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;

    public AuthController(UserRepository users) {
        this.users = users;
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody SiteUser testUser) throws UserNotFoundException {
        SiteUser checkVs = users.findByUsername(testUser.getUsername());
        if (checkVs == null) {
            String messageString = "User Not found: " + testUser.getUsername();
            throw new UserNotFoundException(messageString);
        } else if (PasswordUtils.isMatch(testUser.getPassword(), checkVs.getPassword()) ) {
            return new LoginResponse(checkVs);
        } else {
            return null;
        }
    }

    @PostMapping("/register")
    public SiteUser registerUser(@RequestBody SiteUser neoUser) {
        neoUser.setUserID(KeyUtils.nextKey() );
        neoUser.setPassword(PasswordUtils.encrypt(neoUser.getPassword()) );
        return this.users.save(neoUser);
    }


}
