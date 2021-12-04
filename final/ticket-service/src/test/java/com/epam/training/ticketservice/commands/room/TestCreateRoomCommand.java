package com.epam.training.ticketservice.commands.room;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.model.RoomEntity;
import com.epam.training.ticketservice.database.model.UserEntity;
import com.epam.training.ticketservice.database.repository.RoomRepository;
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
public class TestCreateRoomCommand {
    private CliUserDetailsService.LoggedIn loggedIn;

    @Mock
    RoomRepository roomRepository;

    @InjectMocks
    CreateRoomCommand createRoomCommand;


    private RoomEntity roomEntity;
    private String name;
    private int chairRows;
    private int chairColumn;

    @BeforeEach
    private void setUp() {
        var userEntity = new UserEntity("user", "pw", true);
        loggedIn = new CliUserDetailsService.LoggedIn(userEntity);
        name = "testname";
        chairRows = 5;
        chairColumn = 10;

        roomEntity = new RoomEntity(name, chairRows, chairColumn);
    }

    @AfterEach
    private void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testShouldReturnErrMessageOnUserNotSignedIn() {
        // Given
        var commandResult = createRoomCommand.createRoom(null, 0, 0);
        //Then
        Assertions.assertEquals(commandResult, SecureCommand.notSignedInErr);
    }

    @Test
    public void testShouldCreateRoomOnLoggedInPrivileged() {
        // Given
        Authentication authentication =
                new TestingAuthenticationToken(loggedIn, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //When
        createRoomCommand.createRoom(name, chairRows, chairColumn);

        //Then
        Mockito.verify(roomRepository).save(roomEntity);
    }

}
