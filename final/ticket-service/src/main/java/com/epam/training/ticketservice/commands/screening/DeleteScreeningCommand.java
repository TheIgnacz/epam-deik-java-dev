package com.epam.training.ticketservice.commands.screening;

import com.epam.training.ticketservice.Application;
import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.ParseException;

@ShellComponent
public class DeleteScreeningCommand extends SecureCommand {

    @Autowired
    ScreeningRepository screeningRepository;

    @ShellMethod(key = "delete screening", value = "delete screening")
    public String deleteScreening(String movieName, String roomName, String date) throws ParseException {
        var signedInPrivileged = isUserSignedInPrivileged();
        if (signedInPrivileged.isEmpty()) {
            screeningRepository
                    .findScreeningEntityByMovie_NameAndRoom_NameAndDate(
                            movieName, roomName, Application.simpleDateFormat.parse(date))
                    .ifPresent(screeningRepository::delete);
            return null;
        }
        return signedInPrivileged.get();
    }
}
