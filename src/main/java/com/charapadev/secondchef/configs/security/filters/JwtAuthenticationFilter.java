package com.charapadev.secondchef.configs.security.filters;

import com.charapadev.secondchef.configs.security.authentications.UsernamePasswordAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("jwt.key")
    private String jwtKey;

    @Override
    protected void doFilterInternal(
        @Nullable HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @Nullable FilterChain filterChain
    ) throws ServletException, IOException {
        if (response == null || request == null || filterChain == null) {
            throw new RuntimeException("Response or request or filterChain is null");
        }

        String jwtToken = request.getHeader("Authorization");
        SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jwtToken)
            .getBody();

        String username = String.valueOf(claims.get("username"));
        GrantedAuthority authority =  new SimpleGrantedAuthority("user");
        Authentication auth = new UsernamePasswordAuthentication(
            username,
            null,
            List.of(authority)
        );

        SecurityContextHolder.getContext()
            .setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        boolean isLoginRoute = path.equals("/login");
        boolean isRegisterRoute = path.equals("/users") && method.equals("POST");

        return isLoginRoute || isRegisterRoute;
    }

}
