package com.revature.springskeleton.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils {
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encrypt(String toEncode) {
        return passwordEncoder.encode(toEncode);
    }

    public static boolean isMatch(String test, String key) {
        return (passwordEncoder.matches(test, key));
    }

}
