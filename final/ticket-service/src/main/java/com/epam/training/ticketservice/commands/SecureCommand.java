package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.services.CliUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class SecureCommand {
    public boolean isUserSignedIn() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            printNotSignedIn();
            return false;
        }
        return true;
    }

    public boolean isUserSignedInPrivileged() {
        if (isUserSignedIn()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal() instanceof CliUserDetailsService.LoggedIn) {
                CliUserDetailsService.LoggedIn loggedIn =
                        ((CliUserDetailsService.LoggedIn) authentication.getPrincipal());
                return loggedIn.isPrivileged();
            }
        }
        return false;
    }

    public void printNotSignedIn() {
        System.out.println("You are not signed in");
    }
}
