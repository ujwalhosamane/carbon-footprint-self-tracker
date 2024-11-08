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
import com.quartz.scheduler.job.SixMonthRewardPointUpdateJob;
import com.quartz.scheduler.job.TotalRewardUpdateJob;

import java.util.Calendar;

@Configuration
public class QuartzConfig {
	
	/*
	 * AchievementStatusUpdateJob
	 */
	@Bean
    public JobDetail achievementStatusUpdateJobDetail() {
        return JobBuilder.newJob(AchievementStatusUpdateJob.class)
                .withIdentity("achievementStatusUpdateJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger achievementStatusUpdateJobTrigger() {

        return TriggerBuilder.newTrigger()
                .forJob(achievementStatusUpdateJobDetail())
                .withIdentity("achievementStatusUpdateTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 55 23 L 12,6 ?"))
                .build();
    }
    
    
    
    
    
    /*
	 * SixMonthRewardPointUpdateJob
	 */
    @Bean
    public JobDetail sixMonthRewardPointUpdateJobDetail() {
        return JobBuilder.newJob(SixMonthRewardPointUpdateJob.class)
                .withIdentity("sixMonthRewardPointUpdateJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger sixMonthRewardPointUpdateJobTrigger() {

        return TriggerBuilder.newTrigger()
                .forJob(sixMonthRewardPointUpdateJobDetail())
                .withIdentity("sixMonthRewardPointUpdateJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 57 23 L 12,6 ?"))
                .build();
    }
	
    
    
    
    
    /*
	 * TotalRewardUpdateJob
	 */
    @Bean
    public JobDetail totalRewardUpdateJobDetail() {
        return JobBuilder.newJob(TotalRewardUpdateJob.class)
                .withIdentity("totalRewardUpdateJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger totalRewardUpdateJobTrigger() {

        return TriggerBuilder.newTrigger()
                .forJob(totalRewardUpdateJobDetail())
                .withIdentity("totalRewardUpdateJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 57 23 L 12,6 ?"))
                .build();
    }
    
    
    
    
    
    
    /*
	 * CurrentScoreResetJob
	 */
    @Bean
    public JobDetail currentScoreResetJobDetail() {
        return JobBuilder.newJob(CurrentScoreResetJob.class)
                .withIdentity("currentScoreResetJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger currentScoreResetJobTrigger() {

        return TriggerBuilder.newTrigger()
                .forJob(currentScoreResetJobDetail())
                .withIdentity("currentScoreResetJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 1 0 1 1,7 ?"))
                .build();
    }

    
}
