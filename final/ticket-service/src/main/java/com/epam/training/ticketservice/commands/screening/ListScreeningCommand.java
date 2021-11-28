package com.epam.training.ticketservice.commands.screening;

import com.epam.training.ticketservice.database.model.ScreeningEntity;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@ShellComponent
public class ListScreeningCommand {

    private final ScreeningRepository screeningRepository;

    public ListScreeningCommand(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    @ShellMethod(value = "List the screenings", key = "list screenings")
    public String listScreenings() {
        var screening = screeningRepository.findAll();
        if (screening.isEmpty()) {
            return "There are no screenings";
        }
        return screening.stream()
                .map(ScreeningEntity::toString)
                .collect(Collectors.joining("\n"));
    }
}
