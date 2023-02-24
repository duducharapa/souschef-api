package com.charapadev.secondchef.configs.security.filters;

import com.charapadev.secondchef.configs.security.authentications.UsernamePasswordAuthentication;
import com.charapadev.secondchef.utils.Routes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Filter that intercepts the routes that needs the user to be fully authenticated.
 * <p>
 * The request must have the JWT token provided on successfully authentication in the <b>Authorization</b> header.
 * Having the token, the token will be validated and the information about the users will be refreshed to
 * {@link SecurityContext security context}.
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.key}")
    private String jwtKey;

    @Override
    protected void doFilterInternal(
        @Nullable HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @Nullable FilterChain filterChain
    ) throws ServletException, IOException {
        checkNullableParameters(request, response, filterChain);

        String jwtToken = extractJwtFromHeader(request.getHeader("Authorization"));
        SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

        // Get the roles from the user extracted from JWT
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

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        boolean isLoginRoute = path.equals(Routes.LOGIN_PATH);
        boolean isRegisterRoute = path.equals("/users") && method.equals("POST");

        return isLoginRoute || isRegisterRoute;
    }

    /**
     * Checks if the nullable parameters of {@link #doFilter(ServletRequest, ServletResponse, FilterChain)}
     * method are correctly received.
     *
     * @param request The servlet request object.
     * @param response The servlet response object.
     * @throws RuntimeException If any of parameters is null.
     */
    private void checkNullableParameters(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws RuntimeException {
        if (response == null || request == null || chain == null) {
            throw new RuntimeException("Some of the parameters on doFilterInternal is null");
        }
    }

    private String extractJwtFromHeader(String header) throws BadCredentialsException {
        String BEARER_PREFIX = "Bearer ";

        if (header == null) {
            throw new BadCredentialsException("Token not provided in Authorization header!");
        }

        if (!header.startsWith(BEARER_PREFIX)) {
            throw new BadCredentialsException("The token provided is not a Bearer token!");
        }

        return header.substring(BEARER_PREFIX.length());
    }

}
