package com.carbon.footprint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
public class CarbonFootprintServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarbonFootprintServiceApplication.class, args);
	}

}