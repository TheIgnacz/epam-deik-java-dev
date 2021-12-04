package com.epam.training.ticketservice.commands.movie;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.model.MovieEntity;
import com.epam.training.ticketservice.database.model.UserEntity;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.services.CliUserDetailsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class TestCreateMovieCommand {

    private CliUserDetailsService.LoggedIn loggedIn;

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    CreateMovieCommand createMovieCommand;


    private MovieEntity movieEntity;
    private String name;
    private String genre;
    private int playtime;

    @BeforeEach
    private void setUp() {
        var userEntity = new UserEntity("user", "pw", true);
        loggedIn = new CliUserDetailsService.LoggedIn(userEntity);
        name = "testname";
        genre = "testgenre";
        playtime = 10;

        movieEntity = new MovieEntity(name, genre, playtime);
    }

    @AfterEach
    private void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testShouldReturnErrMessageOnUserNotSignedIn() {
        // Given
        var commandResult = createMovieCommand.createMovie(null, null, 0);
        //Then
        Assertions.assertEquals(commandResult, SecureCommand.notSignedInErr);
    }

    @Test
    public void testShouldCreateMovieOnLoggedInPrivileged() {
        // Given
        Authentication authentication =
                new TestingAuthenticationToken(loggedIn, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //When
        createMovieCommand.createMovie(name, genre, playtime);

        //Then
        Mockito.verify(movieRepository).save(movieEntity);
    }

}
