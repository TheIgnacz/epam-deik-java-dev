package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.services.CliUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public abstract class SecureCommand {

    public static String notSignedInErr = "You are not signed in";

    public Optional<String> isUserSignedIn() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return Optional.of(notSignedInErr);
        }
        return Optional.empty();
    }

    public Optional<String> isUserSignedInPrivileged() {
        var signedIn = isUserSignedIn();
        if (signedIn.isEmpty()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal() instanceof CliUserDetailsService.LoggedIn) {
                CliUserDetailsService.LoggedIn loggedIn =
                        ((CliUserDetailsService.LoggedIn) authentication.getPrincipal());
                return Optional.empty();
            }
        }
        return signedIn;
    }
}
