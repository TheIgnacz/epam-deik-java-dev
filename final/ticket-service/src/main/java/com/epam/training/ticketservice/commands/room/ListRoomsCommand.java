package com.epam.training.ticketservice.commands.room;

import com.epam.training.ticketservice.database.model.RoomsEntity;
import com.epam.training.ticketservice.database.repository.RoomsRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@ShellComponent
public class ListRoomsCommand {

    private final RoomsRepository roomsRepository;

    public ListRoomsCommand(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    @ShellMethod(value = "List the rooms", key = "list rooms")
    public String listRooms() {
        var rooms = roomsRepository.findAll();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        return rooms.stream()
                .map(RoomsEntity::toString)
                .collect(Collectors.joining("\n"));
    }
}
