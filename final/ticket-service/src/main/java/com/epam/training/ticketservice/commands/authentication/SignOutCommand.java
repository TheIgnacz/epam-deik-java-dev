package com.epam.training.ticketservice.commands.authentication;

import com.epam.training.ticketservice.commands.SecureCommand;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignOutCommand extends SecureCommand {

    @ShellMethod(value = "sign out", key = "sign out")
    public String signOut() {
        var signedIn = isUserSignedIn();
        if (signedIn.isEmpty()) {
            SecurityContextHolder.getContext().setAuthentication(null);
            return null;
        }
        return signedIn.get();
    }
}
