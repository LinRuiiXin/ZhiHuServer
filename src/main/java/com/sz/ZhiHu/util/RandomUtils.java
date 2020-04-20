package com.sz.ZhiHu.util;

import java.util.Random;

public class RandomUtils {
    private static Random random = new Random();
    public static Integer getRangeNumber(Integer range){
        return (random.nextInt(range))+1;
    }
}
