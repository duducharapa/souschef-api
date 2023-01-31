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
        http
            .csrf().disable()
            .authorizeRequests((auth) -> auth.antMatchers(HttpMethod.POST, "/users").permitAll())
            .authorizeRequests((auth) -> auth.anyRequest().authenticated())
            .httpBasic();

        return http.build();
    }

}
