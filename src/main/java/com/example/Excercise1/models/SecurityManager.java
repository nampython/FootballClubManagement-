package com.example.Excercise1.models;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityManager {
    /**
     * Returns the name of the currently logged-in user.
     *
     * @return the current user's name
     */
    public static String getUserId() {

        String userId;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object userDetails;
        if (auth != null) {
            userDetails = auth.getPrincipal();
            if (userDetails instanceof UserDetails) {
                userId = ((UserDetails) userDetails).getUsername();
            } else
                return userDetails.toString();

        } else throw new RuntimeException("Unauthorized to make data updates without authentication");

        return userId;

    }
}
