package com.gateway.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                           // Apply to all endpoints
                .allowedOrigins("*")                        // Allow all origins or specify specific ones
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")  // Allow HTTP methods
                .allowedHeaders("*")                        // Allow all headers
                .exposedHeaders("Authorization", "Content-Type") // Expose specific headers to client
                .allowCredentials(true)                     // Allow credentials like cookies or HTTP auth
                .maxAge(3600);                               // Cache preflight requests for 1 hour
    }
}
