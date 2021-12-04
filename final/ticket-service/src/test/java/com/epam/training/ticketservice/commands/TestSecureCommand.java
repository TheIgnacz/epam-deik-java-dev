package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.database.model.UserEntity;
import com.epam.training.ticketservice.services.CliUserDetailsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TestSecureCommand {
    private CliUserDetailsService.LoggedIn loggedIn;

    SecureCommand secureCommand = new SecureCommand() {};

    @BeforeEach
    private void setUp() {
        var userEntity = new UserEntity("user", "pw", true);
        loggedIn = new CliUserDetailsService.LoggedIn(userEntity);
    }

    @AfterEach
    private void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testShouldReturnEmptyOptionalOnSignedInUser() {
        // Given
        Authentication authentication =
                new TestingAuthenticationToken(loggedIn, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Then
        Assertions.assertEquals(secureCommand.isUserSignedIn(), Optional.empty());
    }

    @Test
    public void testShouldReturnFilledOptionalOnNotSignedIn() {
        // Given
        var commandResult = secureCommand.isUserSignedIn();
        //Then
        Assertions.assertFalse(commandResult.isEmpty());
        Assertions.assertEquals(commandResult.get(), SecureCommand.notSignedInErr);
    }

    @Test
    public void testShouldReturnFilledOptionalOnNotSignedInPrivileged() {
        // Given
        var commandResult = secureCommand.isUserSignedInPrivileged();
        //Then
        Assertions.assertFalse(commandResult.isEmpty());
        Assertions.assertEquals(commandResult.get(), SecureCommand.notSignedInErr);
    }

    @Test
    public void testShouldReturnEmptyOptionalOnSignedInPrivileged() {
        // Given
        Authentication authentication =
                new TestingAuthenticationToken(loggedIn, null);


        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Then
        Assertions.assertEquals(secureCommand.isUserSignedInPrivileged(), Optional.empty());
    }
}
