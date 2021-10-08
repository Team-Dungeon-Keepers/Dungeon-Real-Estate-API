package com.revature.springskeleton.controllers;

import com.revature.springskeleton.models.YipMessage;
import com.revature.springskeleton.repositories.YipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/")
public class YipController {
    @Autowired
    private YipRepository yip;

    @GetMapping("/")
    public List<YipMessage> yipFinder() {
        return this.yip.findAll();
    }
}
