package com.charapadev.secondchef.configs.security.providers;

import com.charapadev.secondchef.configs.security.authentications.OtpAuthentication;
import com.charapadev.secondchef.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Provider that validates the second step of authentication using the designed instance.
 * <p>
 * Here, the user provides the username and OTP code to be validated and proceed with JWT token generation.
 *
 * @see OtpAuthentication Otp Authentication.
 */

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private OtpService otpService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Integer code = Integer.parseInt(
            String.valueOf(authentication.getCredentials())
        );

        boolean correctCode = otpService.findOne(code).isPresent();

        if (correctCode) {
            return new OtpAuthentication(username, code);
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OtpAuthentication.class.isAssignableFrom(aClass);
    }

}
