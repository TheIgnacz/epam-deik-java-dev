package com.epam.training.ticketservice.commands.room;

import com.epam.training.ticketservice.database.model.RoomEntity;
import com.epam.training.ticketservice.database.repository.RoomRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@ShellComponent
public class ListRoomsCommand {

    private final RoomRepository roomRepository;

    public ListRoomsCommand(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @ShellMethod(value = "List the rooms", key = "list rooms")
    public String listRooms() {
        var rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        return rooms.stream()
                .map(RoomEntity::toString)
                .collect(Collectors.joining("\n"));
    }
}
