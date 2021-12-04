package com.epam.training.ticketservice.commands.screening;

import com.epam.training.ticketservice.Application;
import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.model.MovieEntity;
import com.epam.training.ticketservice.database.model.RoomEntity;
import com.epam.training.ticketservice.database.model.ScreeningEntity;
import com.epam.training.ticketservice.database.model.UserEntity;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
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

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TestCreateScreeningCommand {
    private CliUserDetailsService.LoggedIn loggedIn;

    @Mock
    ScreeningRepository screeningRepository;

    @Mock
    RoomRepository roomRepository;

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    CreateScreeningCommand createScreeningCommand;


    private ScreeningEntity screeningEntity;
    private MovieEntity movieEntity;
    private RoomEntity roomEntity;
    private String date;
    private String overlappingDate = "2000-10-24 17:27";
    private String noBrakeDate = "2000-10-24 17:33";

    @BeforeEach
    private void setUp() throws ParseException {
        var userEntity = new UserEntity("user", "pw", true);
        loggedIn = new CliUserDetailsService.LoggedIn(userEntity);
        movieEntity = new MovieEntity("name", "genre", 10);
        roomEntity = new RoomEntity("name", 5, 5);
        date = "2000-10-24 17:22";
        screeningEntity = new ScreeningEntity(movieEntity, roomEntity, Application.simpleDateFormat.parse(date));
    }

    @AfterEach
    private void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testShouldReturnErrMessageOnUserNotSignedIn() throws ParseException {
        // Given
        var commandResult = createScreeningCommand.createScreening(null, null, "0");
        //Then
        Assertions.assertEquals(commandResult, SecureCommand.notSignedInErr);
    }

    @Test
    public void testShouldCreateMovieOnLoggedInPrivileged() throws ParseException {
        // Given
        Authentication authentication =
                new TestingAuthenticationToken(loggedIn, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //When
        Mockito.when(movieRepository.findMoviesEntityByName(movieEntity.getName())).thenReturn(Optional.of(movieEntity));
        Mockito.when(roomRepository.findRoomsEntityByName(roomEntity.getName())).thenReturn(Optional.of(roomEntity));
        createScreeningCommand.createScreening(movieEntity.getName(), roomEntity.getName(), date);

        //Then
        Mockito.verify(screeningRepository).save(screeningEntity);
    }

    @Test
    public void testShouldReturnErrorOnOverlappingMovieWhenLoggedInPrivileged() throws ParseException {
        // Given
        Authentication authentication =
                new TestingAuthenticationToken(loggedIn, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //When
        Mockito.when(movieRepository.findMoviesEntityByName(movieEntity.getName())).thenReturn(Optional.of(movieEntity));
        Mockito.when(roomRepository.findRoomsEntityByName(roomEntity.getName())).thenReturn(Optional.of(roomEntity));
        Mockito.when(screeningRepository.findScreeningEntityByRoom(roomEntity)).thenReturn(List.of(screeningEntity));
        createScreeningCommand.createScreening(movieEntity.getName(), roomEntity.getName(), date);


        //Then
        Mockito.verify(screeningRepository).save(screeningEntity);
        var error = createScreeningCommand.createScreening(movieEntity.getName(), roomEntity.getName(), overlappingDate);
        Assertions.assertEquals("There is an overlapping screening", error);

    }

    @Test
    public void testShouldReturnErrorOnNoBrakeWhenLoggedInPrivileged() throws ParseException {
        // Given
        Authentication authentication =
                new TestingAuthenticationToken(loggedIn, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //When
        Mockito.when(movieRepository.findMoviesEntityByName(movieEntity.getName())).thenReturn(Optional.of(movieEntity));
        Mockito.when(roomRepository.findRoomsEntityByName(roomEntity.getName())).thenReturn(Optional.of(roomEntity));
        Mockito.when(screeningRepository.findScreeningEntityByRoom(roomEntity)).thenReturn(List.of(screeningEntity));
        createScreeningCommand.createScreening(movieEntity.getName(), roomEntity.getName(), date);


        //Then
        Mockito.verify(screeningRepository).save(screeningEntity);
        var error = createScreeningCommand.createScreening(movieEntity.getName(), roomEntity.getName(), noBrakeDate);
        Assertions.assertEquals("This would start in the break period after another screening in this room", error);

    }

}
