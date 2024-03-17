package com.vicarius.apiquota.common.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DaytimeSelector {

    private static final LocalTime DAY_START = LocalTime.of(6, 0); // 6:00 AM
    private static final LocalTime NIGHT_START = LocalTime.of(18, 0); // 6:00 PM

    public boolean isDaytime() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.isAfter(DAY_START) && currentTime.isBefore(NIGHT_START);
    }
}