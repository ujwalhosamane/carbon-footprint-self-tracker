package com.quartz.scheduler.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminParameter {

    @Id
    @Column(unique = true)
    private String parameterKey;

    private String parameterValue; 

}
