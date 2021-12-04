package com.epam.training.ticketservice.commands.authentication;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.services.CliUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DescribeAccountCommand extends SecureCommand {

    @ShellMethod(key = "describe account", value = "describe account")
    public String describeAccount() {
        var signedIn = isUserSignedIn();
        if (signedIn.isEmpty()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal() instanceof CliUserDetailsService.LoggedIn) {
                CliUserDetailsService.LoggedIn loggedIn =
                        ((CliUserDetailsService.LoggedIn) authentication.getPrincipal());
                if (loggedIn.isPrivileged()) {
                    return String.format("Signed in with privileged account '%s'", loggedIn.getUsername());
                }
            }
        }
        return signedIn.get();
    }
}
