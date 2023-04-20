package com.charapadev.secondchef.utils;

import com.charapadev.secondchef.models.auth.Authority;

/**
 * Class used to describe routes and declare them as protected or not.
 * <p>
 * Unprotected routes are public endpoints accessible to any user.
 * <p>
 * Protected routes are private routes that can only access if the user is authenticated.
 * Also exists another levels of security like authorities, like described on Spring Security framework.
 *
 * @see Authority Authority model.
 */

public class Routes {

    /**
     * Route used to users authenticate on application.
     */
    public final static String LOGIN = "/login";

}
