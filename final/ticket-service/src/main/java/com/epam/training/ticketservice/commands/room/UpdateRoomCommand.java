package com.epam.training.ticketservice.commands.room;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UpdateRoomCommand extends SecureCommand {

    @Autowired
    RoomRepository roomRepository;

    @ShellMethod(key = "update room", value = "update room")
    public String updateRoom(String name, int chairRows, int chairColumns) {
        var signedInPrivileged = isUserSignedInPrivileged();
        if (signedInPrivileged.isEmpty()) {
            var roomOpt = roomRepository.findRoomsEntityByName(name);
            if (roomOpt.isPresent()) {
                var room = roomOpt.get();
                room.setName(name);
                room.setChairRows(chairRows);
                room.setChairColumn(chairColumns);
                roomRepository.save(room);
            }
            return null;
        }
        return signedInPrivileged.get();
    }
}
