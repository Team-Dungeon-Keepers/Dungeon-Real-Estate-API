package com.revature.springskeleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.revature.springskeleton.controllers"})
@EntityScan(basePackages = {"com.revature.springskeleton.models"})
public class SpringSkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSkeletonApplication.class, args);
        System.out.println("Seems to be working.");
    }

}
