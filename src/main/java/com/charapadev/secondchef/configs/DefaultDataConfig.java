package com.charapadev.secondchef.configs;

import com.charapadev.secondchef.services.DefaultDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultDataConfig {

    @Bean
    CommandLineRunner run(DefaultDataService defaultDataService) {
        return args -> defaultDataService.loadDefaultData();
    }

}
