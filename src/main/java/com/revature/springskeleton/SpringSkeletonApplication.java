package com.revature.springskeleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class SpringSkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSkeletonApplication.class, args);
        System.out.println("Seems to be working.");
    }

}
