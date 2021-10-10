package com.revature.springskeleton;

import com.revature.springskeleton.controllers.YipController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringSkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSkeletonApplication.class, args);
        System.out.println("Seems to be working.");
    }

}
