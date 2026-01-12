package com.evergreen.EvergreenServer.schedulers;

import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Service
public class SampleScheduler {


    // rules
    // lockAtLeastFor ≤ job interval, often slightly smaller than fixedRate
    // lockAtMostFor ≥ maximum expected job duration + some buffer

    @Scheduled(fixedRate = 5000)
    @SchedulerLock(name = "logToConsoleName", lockAtMostFor = "10s", lockAtLeastFor = "4s")
    public void logToConsole() {
        System.out.println("Executing task at " + new Date());
    }

}
