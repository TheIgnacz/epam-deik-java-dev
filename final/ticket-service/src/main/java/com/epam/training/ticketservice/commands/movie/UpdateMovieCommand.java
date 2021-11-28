package com.epam.training.ticketservice.commands.movie;

import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UpdateMovieCommand extends SecureCommand {

    @Autowired
    MovieRepository movieRepository;

    @ShellMethod(key = "update movie", value = "update movie")
    public void updateMovie(String name, String genre, int playtime) {
        if (isUserSignedInPrivileged()) {
            var movieOpt = movieRepository.findMoviesEntityByName(name);
            if (movieOpt.isPresent()) {
                var movie = movieOpt.get();
                movie.setName(name);
                movie.setGenre(genre);
                movie.setPlaytime(playtime);
                movieRepository.save(movie);
            }
        }
    }
}
