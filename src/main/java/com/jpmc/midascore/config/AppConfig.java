// Declares this file is part of the "com.jpmc.midascore.config" package
package com.jpmc.midascore.config;

// Imports the @Bean annotation, which tells Spring to manage a method's return value as a bean
import org.springframework.context.annotation.Bean;

// Imports the @Configuration annotation, which marks this class as a source of Spring beans
import org.springframework.context.annotation.Configuration;

// Imports the RestTemplate class, which lets us make HTTP requests (like POST/GET)
import org.springframework.web.client.RestTemplate;

// Marks this class as a configuration class for Spring (like a setup or settings file)
@Configuration
public class AppConfig {

    // Declares a method that returns a RestTemplate object as a Spring-managed bean
    @Bean
    public RestTemplate restTemplate() {
        // Creates a new instance of RestTemplate, which we will use to send HTTP requests
        return new RestTemplate();
    }
}
