package com.epam.training.ticketservice.commands.movie;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.model.MoviesEntity;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateMovieCommand extends SecureCommand {

    @Autowired
    MovieRepository movieRepository;

    @ShellMethod(key = "create movie", value = "create movie")
    public void createMovie(String name, String genre, int playtime) {
        if (isUserSignedInPrivileged()) {
            movieRepository.save(new MoviesEntity(name, genre, playtime));
        }
    }
}
