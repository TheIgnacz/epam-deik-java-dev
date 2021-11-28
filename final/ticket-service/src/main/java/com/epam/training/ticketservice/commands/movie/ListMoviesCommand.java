package com.epam.training.ticketservice.commands.movie;

import com.epam.training.ticketservice.database.model.MoviesEntity;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@ShellComponent
public class ListMoviesCommand {

    private final MovieRepository movieRepository;

    public ListMoviesCommand(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @ShellMethod(value = "List the movies", key = "list movies")
    public String listMovies() {
        var movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        }
        return movies.stream()
                .map(MoviesEntity::toString)
                .collect(Collectors.joining("\n"));
    }
}
