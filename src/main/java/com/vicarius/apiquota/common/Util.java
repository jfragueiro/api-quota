package com.vicarius.apiquota.common;

import java.time.LocalTime;

public class Util {
    public static boolean isDaytime() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.isAfter(LocalTime.of(8, 59)) && currentTime.isBefore(LocalTime.of(17, 1));
    }
}
