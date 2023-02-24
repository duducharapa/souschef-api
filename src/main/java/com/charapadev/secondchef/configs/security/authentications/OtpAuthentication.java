package com.charapadev.secondchef.configs.security.authentications;

import com.charapadev.secondchef.configs.security.providers.OtpAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * {@link Authentication Authentication} implementation used for OTP step validation.
 * <p>
 *  When the users try to authenticate on application, the users should provide a numeric code of unique use
 *  to receive the JWT token.
 *
 * @see OtpAuthenticationProvider OTP Provider.
 */

public class OtpAuthentication extends UsernamePasswordAuthenticationToken {

    public OtpAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public OtpAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

}
