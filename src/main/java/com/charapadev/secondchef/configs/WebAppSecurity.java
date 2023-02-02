package com.charapadev.secondchef.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebAppSecurity {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Security configs
        http.csrf().disable();

        // Authentication and authorization configs
        http.httpBasic();

        // Endpoint access configs
        http
            .authorizeRequests((auth) -> auth.mvcMatchers(HttpMethod.POST, "/users").permitAll())
            .authorizeRequests((auth) -> auth.anyRequest().authenticated());

        return http.build();
    }

}
