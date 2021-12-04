package com.epam.training.ticketservice.commands.movie;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DeleteMovieCommand extends SecureCommand {

    @Autowired
    MovieRepository movieRepository;

    @ShellMethod(key = "delete movie", value = "delete movie")
    public String deleteMovie(String name) {
        var signedInPrivileged = isUserSignedInPrivileged();
        if (signedInPrivileged.isEmpty()) {
            movieRepository.findMoviesEntityByName(name)
                    .ifPresent(movieRepository::delete);
            return null;
        }
        return signedInPrivileged.get();
    }
}
