package com.epam.training.ticketservice.commands.screening;

import com.epam.training.ticketservice.Application;
import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.model.MoviesEntity;
import com.epam.training.ticketservice.database.model.RoomsEntity;
import com.epam.training.ticketservice.database.model.ScreeningEntity;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.RoomsRepository;
import com.epam.training.ticketservice.database.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@ShellComponent
public class CreateScreeningCommand extends SecureCommand {

    @Autowired
    RoomsRepository roomsRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ScreeningRepository repo;

    @ShellMethod(key = "create screening", value = "create screening")
    public void createScreening(String movieName, String roomName, String date) throws ParseException {
        if (isUserSignedInPrivileged()) {
            Date parsed = Application.simpleDateFormat.parse(date);
            Optional<MoviesEntity> optionalMovie = movieRepository.findMoviesEntityByName(movieName);
            Optional<RoomsEntity> optionalRoom = roomsRepository.findRoomsEntityByName(roomName);
            List<ScreeningEntity> inRoomList =
                    repo.findScreeningEntityByRoom(optionalRoom.orElse(null));
            if (!inRoomList.isEmpty()) {
                Instant instant = parsed.toInstant();
                Optional<ScreeningError> error =
                        inRoomList.stream()
                                .map(screeningEntity -> {
                                    var playtime = optionalMovie.map(MoviesEntity::getPlaytime).orElse(0);
                                    return ScreeningError.getScreeningError(screeningEntity, playtime, instant);
                                }).filter(Predicate.not(ScreeningError.NONE::equals)).findFirst();
                if (error.isPresent()) {
                    switch (error.get()) {
                        case OVERLAPPING:
                            System.out.println("There is an overlapping screening");
                            break;
                        case NO_BREAK:
                            System.out.println("This would start in the break"
                                    + " period after another screening in this room");
                            break;
                        default:
                            break;
                    }
                    return;
                }
            }
            repo.save(new ScreeningEntity(optionalMovie.orElse(null), optionalRoom.orElse(null), parsed));
        }
    }

    public enum ScreeningError {
        NONE, OVERLAPPING, NO_BREAK;

        public static ScreeningError getScreeningError(ScreeningEntity screeningEntity,
                                                       Integer playtime,
                                                       Instant instant) {
            var screeningDate = screeningEntity.getDate().toInstant();
            var screeningEnd = screeningDate.plus(screeningEntity.getMovie().getPlaytime(), ChronoUnit.MINUTES);
            var screeningWithBreak = screeningEnd.plus(10, ChronoUnit.MINUTES);
            if (instant.isAfter(screeningDate)) {
                if (instant.isBefore(screeningEnd)) {
                    return ScreeningError.OVERLAPPING;

                } else if (instant.isBefore(screeningWithBreak)) {
                    return ScreeningError.NO_BREAK;
                }
                return ScreeningError.NONE;
            } else if (instant.isBefore(screeningEnd)
                    && instant.plus(playtime, ChronoUnit.MINUTES).isAfter(screeningEnd)) {
                return ScreeningError.OVERLAPPING;
            } else if (instant.isBefore(screeningWithBreak)
                    && instant.plus(playtime, ChronoUnit.MINUTES).isAfter(screeningWithBreak)) {
                return ScreeningError.NO_BREAK;
            }
            return ScreeningError.NONE;
        }
    }
}
