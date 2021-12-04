package com.epam.training.ticketservice.commands.authentication;

import com.epam.training.ticketservice.commands.SecureCommand;
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

@ExtendWith(MockitoExtension.class)
public class TestDescribeAccountCommand {

    private CliUserDetailsService.LoggedIn loggedIn;

    DescribeAccountCommand describeAccountCommand = new DescribeAccountCommand();

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
    public void testShouldReturnErrStringOnUserNotLoggedIn() {
        // Given
        var commandResult = describeAccountCommand.describeAccount();
        //Then
        Assertions.assertEquals(commandResult, SecureCommand.notSignedInErr);
    }

    @Test
    public void testShouldReturnAccountDescriptionOnUserSignedIn() {
        // Given
        Authentication authentication =
                new TestingAuthenticationToken(loggedIn, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String expected = String.format("Signed in with privileged account '%s'", loggedIn.getUsername());

        //Then
        Assertions.assertEquals(describeAccountCommand.describeAccount(), expected);
    }


}
