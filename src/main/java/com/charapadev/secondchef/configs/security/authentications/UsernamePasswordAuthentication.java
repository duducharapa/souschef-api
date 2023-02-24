package com.charapadev.secondchef.configs.security.authentications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * {@link Authentication Authentication} implementation used on usual authentications: the username and password.
 * <p>
 * This class is used on the first step of authentication: the user provide the email(used as username) and the password
 * to check if exists on application.
 *
 * @see com.charapadev.secondchef.configs.security.providers.UsernamePasswordAuthenticationProvider Initial provider.
 */

public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

    public UsernamePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UsernamePasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
