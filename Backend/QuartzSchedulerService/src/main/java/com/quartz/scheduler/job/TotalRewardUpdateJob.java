package com.quartz.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quartz.scheduler.client.UserClient;

@Component
public class TotalRewardUpdateJob implements Job  {
	@Autowired
	private UserClient userClient;
	
	private static final Logger logger = LoggerFactory.getLogger(CurrentScoreResetJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		userClient.getAndUpdateRewardPoints();
		logger.info("Successfully updated total reward points");
	}

}
