package com.epam.training.ticketservice.commands.room;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.model.RoomsEntity;
import com.epam.training.ticketservice.database.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateRoomCommand extends SecureCommand {

    @Autowired
    RoomsRepository roomsRepository;

    @ShellMethod(key = "create room", value = "create room")
    public void createRoom(String name, int chairRows, int chairColumns) {
        if (isUserSignedInPrivileged()) {
            roomsRepository.save(new RoomsEntity(name, chairRows, chairColumns));
        }
    }
}
