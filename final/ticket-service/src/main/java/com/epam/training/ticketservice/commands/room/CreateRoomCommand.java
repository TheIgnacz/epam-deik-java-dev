package com.epam.training.ticketservice.commands.room;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.model.RoomEntity;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateRoomCommand extends SecureCommand {

    @Autowired
    RoomRepository roomRepository;

    @ShellMethod(key = "create room", value = "create room")
    public String createRoom(String name, int chairRows, int chairColumns) {
        var signedInPrivileged = isUserSignedInPrivileged();
        if (signedInPrivileged.isEmpty()) {
            roomRepository.save(new RoomEntity(name, chairRows, chairColumns));
            return null;
        }
        return signedInPrivileged.get();
    }
}
