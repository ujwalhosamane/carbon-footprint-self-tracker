package com.quartz.scheduler.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.quartz.scheduler.entity.AdminParameter;

@Component
public class AdminParameterInitializer implements CommandLineRunner {

    @Autowired
    private AdminParameterRepository adminParameterRepository;

    @Override
    public void run(String... args) throws Exception {
        adminParameterRepository.save(
        new AdminParameter("retentionDurationInMonths", "12"));
    }
}