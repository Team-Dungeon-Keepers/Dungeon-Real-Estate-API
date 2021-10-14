package com.revature.dungeonsite.utils;

import java.util.Date;

public class KeyUtils {
    public static long nextKey() {
        Date tempDate = new Date();
        return tempDate.getTime();
    }
}
