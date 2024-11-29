package com.carbon.footprint.suggestion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.carbon.footprint.suggestion.client.GeminiClient;

@Configuration
public class Configure {
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }
	
	@Bean
	public GeminiClient geminiClient() {
		return new GeminiClient();
	}
}
