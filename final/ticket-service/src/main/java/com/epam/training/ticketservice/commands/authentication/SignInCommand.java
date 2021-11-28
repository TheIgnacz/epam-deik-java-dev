package com.epam.training.ticketservice.commands.authentication;

import com.epam.training.ticketservice.commands.SecureCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignInCommand extends SecureCommand {

    @Autowired
    AuthenticationManager authenticationManager;

    @ShellMethod(value = "sign in privileged", key = "sign in privileged")
    public void signIn(String name, String pw) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(name, pw);
        try {
            Authentication result = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(result);
            //SecurityContextHolder.getContext().setAuthentication(null);
        } catch (AuthenticationException authenticationException) {
            System.out.println("Login failed due to incorrect credentials");
        }
    }
}
