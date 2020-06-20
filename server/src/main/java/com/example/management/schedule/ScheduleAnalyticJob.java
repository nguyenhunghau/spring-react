package com.example.management.schedule;

import com.example.management.component.EmailUtils;
import com.example.management.service.WebAnalyticService;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
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
    
    @Autowired
    private EmailUtils emailUtils;
    
    @Scheduled(cron = "0 0 7-20 ? * *")
    public void schedule() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String timeStart = format.format(new Date());
        Map<String, Integer> map = webAnalyticService.analytics();
        
        String bodyMail = "Start time: " + timeStart + "<br>"
                + "End time: " + format.format(new Date()) + "<br>"
                + "Total result of It Viec: " + map.get("itviec") + "<br>"
                + "Total result of VietnamWork: " + map.get("vnwork") + "<br>";
        String subject = "Finish analyst Job of ItViec and VietnamWork";
        emailUtils.sendMail(
                subject,
                bodyMail,
                Collections.singletonList("nguyenhunghau.us@gmail.com"),
                null,
                null
        );
    }
    
    @Scheduled(cron = "0 30 * ? * *")
    public void scheduleAnalystDetailJob() {
        webAnalyticService.analyticsDetail(30);
    }
}
