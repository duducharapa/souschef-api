package com.charapadev.secondchef.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderService implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomUserDetails userFound = userDetailsService.loadUserByUsername(username);

        return checkPassword(password, userFound);
    }

    private Authentication checkPassword(String rawPassword, CustomUserDetails user) throws BadCredentialsException {
        String encodedPassword = user.getPassword();
        System.out.println(encodedPassword);
        System.out.println(rawPassword);
        boolean passwordMatches = passwordEncoder.matches(rawPassword, encodedPassword);

        if (passwordMatches) {
            return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
            );
        } else {
            throw new BadCredentialsException("Something went wrong");
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
