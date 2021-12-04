package com.epam.training.ticketservice.commands.movie;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.model.MovieEntity;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateMovieCommand extends SecureCommand {

    @Autowired
    MovieRepository movieRepository;

    @ShellMethod(key = "create movie", value = "create movie")
    public String createMovie(String name, String genre, int playtime) {
        var signedInPrivileged = isUserSignedInPrivileged();
        if (signedInPrivileged.isEmpty()) {
            movieRepository.save(new MovieEntity(name, genre, playtime));
            return null;
        }
        return signedInPrivileged.get();
    }
}
