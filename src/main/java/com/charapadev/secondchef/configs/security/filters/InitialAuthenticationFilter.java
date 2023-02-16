package com.charapadev.secondchef.configs.security.filters;

import com.charapadev.secondchef.configs.security.authentications.OtpAuthentication;
import com.charapadev.secondchef.configs.security.authentications.UsernamePasswordAuthentication;
import com.charapadev.secondchef.utils.Routes;
import com.charapadev.secondchef.utils.generators.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter responsible to intercept the authentication requests to path /login.
 * The full authentication consists in two steps:
 * <p>* The common step that consists username/password that generates the OTP code.
 * <p>* The username/code, where the code is an OTP generated on the earlier step.
 * <p>
 *  After these steps, the user will receive a JWT token used as identification on authenticated routes.
 */

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Override
    protected void doFilterInternal(
        @Nullable HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @Nullable FilterChain filterChain
    ) {
        checkNullableParameters(request, response);

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        // If the CODE is null, the filter try to perform the first step of authentication.
        // Otherwise, perform the OTP verification and generates the JWT if validated.
        if (code == null) {
            Authentication auth = new UsernamePasswordAuthentication(username, password);
            authenticationManager.authenticate(auth);
        } else {
            Authentication auth = new OtpAuthentication(username, code);
            authenticationManager.authenticate(auth);

            String jwtToken = jwtGenerator.generate(username);
            response.setHeader("Authorization", jwtToken);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals(Routes.LOGIN_PATH);
    }

    /**
     * Checks if the nullable parameters of {@link #doFilter(ServletRequest, ServletResponse, FilterChain)}
     * method are correctly received.
     * 
     * @param request The servlet request object.
     * @param response The servlet response object.
     */
    private void checkNullableParameters(HttpServletRequest request, HttpServletResponse response) {
        if (response == null || request == null) {
            throw new RuntimeException("Some of the parameters of doFilterInternal is null");
        }
    }

}
