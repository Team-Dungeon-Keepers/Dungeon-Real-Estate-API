package com.revature.springskeleton.utils;

import java.util.Date;

public class KeyUtils {
    public static long nextKey() {
        Date tempDate = new Date();

        System.out.println(tempDate.getTime() );
        return tempDate.getTime();
    }
}
