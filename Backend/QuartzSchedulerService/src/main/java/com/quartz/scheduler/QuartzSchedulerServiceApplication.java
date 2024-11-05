package com.quartz.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class QuartzSchedulerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuartzSchedulerServiceApplication.class, args);
	}

}
