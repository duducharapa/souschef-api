package com.charapadev.secondchef.configs.security.providers;

import com.charapadev.secondchef.configs.security.CustomUserDetails;
import com.charapadev.secondchef.configs.security.authentications.UsernamePasswordAuthentication;
import com.charapadev.secondchef.services.JpaUserDetailsService;
import com.charapadev.secondchef.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Provider that validates the first step of authentication.
 * <p>
 * Here, the user provides the username and password to, if correct, generates an OTP to validate on
 * next step.
 *
 * @see UsernamePasswordAuthentication Username Password Authentication.
 * @see OtpAuthenticationProvider Second Step Authentication Provider.
 */

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private OtpService otpService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        CustomUserDetails userFound = findUser(username);
        boolean isSamePassword = passwordMatches(password, userFound.getPassword());

        if (isSamePassword) {
            // Generate the otp code for next authentication step
            otpService.create();

            return new UsernamePasswordAuthentication(username, password);
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(aClass);
    }

    /**
     * Searches an instance of {@link CustomUserDetails user details} using the username given.
     * <p>
     * To search the users, an implementation of {@link UserDetails UserDetails} is used to retrieve it.
     * If the user is not found, the authentication process will not proceed.
     *
     * @param username The username to search.
     * @return The user found.
     * @see JpaUserDetailsService User Details implementation.
     */
    private CustomUserDetails findUser(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    /**
     * Checks the two passwords provided to verify if matches.
     * <p>
     * To match these, the method uses a password encoder provided from Spring Security.
     *
     * @param rawPassword The password without hashing.
     * @param encodedPassword The hashed password.
     * @return If these two password matches.
     * @see BCryptPasswordEncoder BCrypt Password Encoder.
     */
    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

}
