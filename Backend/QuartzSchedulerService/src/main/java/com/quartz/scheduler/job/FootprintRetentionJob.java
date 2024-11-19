package com.quartz.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quartz.scheduler.client.FootprintClient;
import com.quartz.scheduler.repository.AdminParameterRepository;

@Component
public class FootprintRetentionJob implements Job {
	@Autowired
	private FootprintClient footprintClient;
	
	@Autowired
	private AdminParameterRepository adminParameterRepository;

	private static final Logger logger = LoggerFactory.getLogger(FootprintRetentionJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String parameterKey = "retentionDurationInMonths";
		footprintClient.deleteOldFootprints(
				Integer.parseInt(
						adminParameterRepository
						.findById(parameterKey)
						.get()
						.getParameterValue()));
		logger.info("Successfully reset the current score");
	}
}
