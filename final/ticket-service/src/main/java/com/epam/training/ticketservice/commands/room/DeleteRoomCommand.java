package com.epam.training.ticketservice.commands.room;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DeleteRoomCommand extends SecureCommand {

    @Autowired
    RoomsRepository roomsRepository;

    @ShellMethod(key = "delete room", value = "delete room")
    public void deleteRoom(String name) {
        if (isUserSignedInPrivileged()) {
            roomsRepository.findRoomsEntityByName(name)
                    .ifPresent(roomsRepository::delete);
        }
    }
}
