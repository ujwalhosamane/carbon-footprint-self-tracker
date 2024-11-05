package com.carbon.footprint.globalInsight.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carbon.footprint.globalInsight.model.GlobalInsight;

@Repository
public interface InsightRepository extends JpaRepository<GlobalInsight, Long> {
	public List<GlobalInsight> findByUserId(long userId);
	public List<GlobalInsight> findByDateAfter(LocalDate date);
	public List<GlobalInsight> findByDate(LocalDate date);
	public void deleteByUserId(long userId);
}
