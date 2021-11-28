package com.epam.training.ticketservice.commands.room;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UpdateRoomCommand extends SecureCommand {

    @Autowired
    RoomsRepository roomsRepository;

    @ShellMethod(key = "update room", value = "update room")
    public void updateRoom(String name, int chairRows, int chairColumns) {
        if (isUserSignedInPrivileged()) {
            var roomOpt = roomsRepository.findRoomsEntityByName(name);
            if (roomOpt.isPresent()) {
                var room = roomOpt.get();
                room.setName(name);
                room.setChairRows(chairRows);
                room.setChairColumn(chairColumns);
                roomsRepository.save(room);
            }
        }
    }
}
