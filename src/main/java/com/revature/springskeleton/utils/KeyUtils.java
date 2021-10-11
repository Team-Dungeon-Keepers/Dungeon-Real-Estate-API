package com.revature.springskeleton.utils;

import java.util.Date;

public class KeyUtils {
    public static long nextID() {
        Date tempDate = new Date();
        return tempDate.getTime();
    }
}
