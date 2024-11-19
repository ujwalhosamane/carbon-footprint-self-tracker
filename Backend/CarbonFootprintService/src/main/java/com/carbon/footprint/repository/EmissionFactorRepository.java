package com.carbon.footprint.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbon.footprint.model.EmissionFactor;

public interface EmissionFactorRepository extends JpaRepository<EmissionFactor, Long> {

}
