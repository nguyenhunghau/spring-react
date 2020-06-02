package com.example.management.schedule;

import com.example.management.service.WebAnalyticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nguyen Hung Hau
 */

@Component
public class ScheduleAnalyticJob {
    
    @Autowired
    private WebAnalyticService webAnalyticService;
    
    @Scheduled(fixedRate = 1000)
    public void schedule() {
        webAnalyticService.testCron();
    }
}
