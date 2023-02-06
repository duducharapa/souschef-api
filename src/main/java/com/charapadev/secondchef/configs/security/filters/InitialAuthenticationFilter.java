package com.charapadev.secondchef.configs.security.filters;

import com.charapadev.secondchef.configs.security.authentications.OtpAuthentication;
import com.charapadev.secondchef.configs.security.authentications.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.key}")
    private String jwtKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(
        @Nullable HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @Nullable FilterChain filterChain
    ) {
        if (response == null || request == null) {
            throw new RuntimeException("Response or request is null");
        }

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        if (code == null) {
            Authentication auth = new UsernamePasswordAuthentication(username, password);
            authenticationManager.authenticate(auth);
        } else {
            Authentication auth = new OtpAuthentication(username, code);
            authenticationManager.authenticate(auth);

            String jwtToken = generateJwt(username);
            response.setHeader("Authorization", jwtToken);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }

    private String generateJwt(String username) {
        SecretKey key = Keys.hmacShaKeyFor(
            jwtKey.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.builder()
            .setClaims(Map.of("username" , username))
            .signWith(key)
            .compact();
    }

}
