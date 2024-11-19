package com.quartz.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quartz.scheduler.entity.AdminParameter;

public interface AdminParameterRepository extends JpaRepository<AdminParameter, String> {

}
