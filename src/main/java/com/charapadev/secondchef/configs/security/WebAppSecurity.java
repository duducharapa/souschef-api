package com.charapadev.secondchef.configs.security;

import com.charapadev.secondchef.configs.security.providers.OtpAuthenticationProvider;
import com.charapadev.secondchef.configs.security.providers.UsernamePasswordAuthenticationProvider;
import com.charapadev.secondchef.configs.security.filters.InitialAuthenticationFilter;
import com.charapadev.secondchef.configs.security.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebAppSecurity {

    @Lazy
    @Autowired
    private InitialAuthenticationFilter initialAuthenticationFilter;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
            .authenticationProvider(usernamePasswordAuthenticationProvider)
            .authenticationProvider(otpAuthenticationProvider);

        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Security configurations
        http.csrf().disable();

        // Custom filters configurations
        http
            .addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class)
            .addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

        // Endpoint access configurations
        http
            .authorizeRequests((auth) -> auth.mvcMatchers(HttpMethod.POST, "/users").permitAll())
            .authorizeRequests((auth) -> auth.anyRequest().authenticated());

        return http.build();
    }

}
