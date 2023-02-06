package com.charapadev.secondchef.configs.security.providers;

import com.charapadev.secondchef.configs.security.CustomUserDetails;
import com.charapadev.secondchef.services.JpaUserDetailsService;
import com.charapadev.secondchef.configs.security.authentications.UsernamePasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        CustomUserDetails userFound = findUser(username);
        boolean isSamePassword = passwordMatches(password, userFound.getPassword());

        if (isSamePassword) {
            return new UsernamePasswordAuthentication(username, password);
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(aClass);
    }

    private CustomUserDetails findUser(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

}
