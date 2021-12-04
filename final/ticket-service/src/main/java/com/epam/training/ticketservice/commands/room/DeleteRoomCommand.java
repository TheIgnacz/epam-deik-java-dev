package com.epam.training.ticketservice.commands.room;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DeleteRoomCommand extends SecureCommand {

    @Autowired
    RoomRepository roomRepository;

    @ShellMethod(key = "delete room", value = "delete room")
    public String deleteRoom(String name) {
        var signedInPrivileged = isUserSignedInPrivileged();
        if (signedInPrivileged.isEmpty()) {
            roomRepository.findRoomsEntityByName(name)
                    .ifPresent(roomRepository::delete);
            return null;
        }
        return signedInPrivileged.get();
    }
}
