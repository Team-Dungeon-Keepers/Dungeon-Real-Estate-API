package com.revature.springskeleton.controllers;

import com.revature.springskeleton.models.LoginResponse;
import com.revature.springskeleton.models.SiteUser;
import com.revature.springskeleton.exceptions.UserNotFoundException;
import com.revature.springskeleton.repositories.UserRepository;
import com.revature.springskeleton.utils.KeyUtils;
import com.revature.springskeleton.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository users;

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody SiteUser testUser) throws UserNotFoundException {
        SiteUser checkVs = users.findByUsername(testUser.getUsername());
        if (checkVs == null) {
            String messageString = "User Not found: " + testUser.getUsername();
            throw new UserNotFoundException(messageString);
        } else if ( PasswordUtils.isMatch(testUser.getPassword(), checkVs.getPassword()) ) {
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
