package com.quartz.scheduler.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.quartz.scheduler.job.AchievementStatusUpdateJob;
import com.quartz.scheduler.job.CurrentScoreResetJob;

import java.util.Calendar;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail currentScoreResetJobDetail() {
        return JobBuilder.newJob(CurrentScoreResetJob.class)
                .withIdentity("currentScoreResetJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger currentScoreResetJobTrigger() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 120); // Start after 120 seconds

        return TriggerBuilder.newTrigger()
                .forJob(currentScoreResetJobDetail())
                .withIdentity("currentScoreResetJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 1 0 1 1,7 ?"))
                //.withSchedule(CronScheduleBuilder.cronSchedule("0 13 17 5 * ?"))
                .build();
    }

    @Bean
    public JobDetail achievementStatusUpdateJobDetail() {
        return JobBuilder.newJob(AchievementStatusUpdateJob.class)
                .withIdentity("achievementStatusUpdateJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger achievementStatusUpdateJobTrigger() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 60); // Start after 60 seconds

        return TriggerBuilder.newTrigger()
                .forJob(achievementStatusUpdateJobDetail())
                .withIdentity("achievementStatusUpdateTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 1 0 1 1,7 ?"))
                //.withSchedule(CronScheduleBuilder.cronSchedule("0 13 17 5 * ?"))
                .build();
    }
}
