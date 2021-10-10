package com.revature.springskeleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@ComponentScan(basePackages = {"com.revature.springskeleton.controllers",
        "com.revature.springskeleton.models",
        "com.revature.springskeleton.repositories"
})
public class SpringSkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSkeletonApplication.class, args);
        System.out.println("Seems to be working.");
    }

}
