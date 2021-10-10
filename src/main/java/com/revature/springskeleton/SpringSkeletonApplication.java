package com.revature.springskeleton;

import com.revature.springskeleton.controllers.YipController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSkeletonApplication.class, args);
        System.out.println("Seems to be working.");
    }

}
