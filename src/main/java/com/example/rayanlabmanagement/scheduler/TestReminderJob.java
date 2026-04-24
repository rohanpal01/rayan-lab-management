package com.example.rayanlabmanagement.scheduler;

import org.quartz.*;
import org.springframework.stereotype.Component;

@Component
public class TestReminderJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("Reminder: Run scheduled tests!");
    }
}
