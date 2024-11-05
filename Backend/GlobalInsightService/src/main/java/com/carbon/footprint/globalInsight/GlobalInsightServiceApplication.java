package com.carbon.footprint.globalInsight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GlobalInsightServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalInsightServiceApplication.class, args);
	}

}
