package com.example.management.controller;

//<editor-fold defaultstate="collapsed" desc="IMPORT">
import com.example.management.schedule.ScheduleAnalyticJob;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//</editor-fold>

/**
 *
 * @author Nguyen HUng Hau
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private static final String SCHEDULED_TASKS = "scheduledTasks";

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @Autowired
    private ScheduleAnalyticJob scheduleAnalyticJob;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/stop")
    public String stopSchedule() {
        postProcessor.postProcessBeforeDestruction(scheduleAnalyticJob, SCHEDULED_TASKS);
        return "OK";
    }

    @GetMapping(value = "/start")
    public String startSchedule() {
        postProcessor.postProcessAfterInitialization(scheduleAnalyticJob, SCHEDULED_TASKS);
        return "OK";
    }

    @GetMapping(value = "/getAll")
    public String listSchedules() throws JsonProcessingException {
        Set<ScheduledTask> setTasks = postProcessor.getScheduledTasks();
        if (!setTasks.isEmpty()) {
            return objectMapper.writeValueAsString(setTasks);
        } else {
            return "No running tasks !";
        }
    }
}
