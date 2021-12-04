package com.epam.training.ticketservice.commands.screening;

import com.epam.training.ticketservice.Application;
import com.epam.training.ticketservice.commands.SecureCommand;
import com.epam.training.ticketservice.database.model.MovieEntity;
import com.epam.training.ticketservice.database.model.RoomEntity;
import com.epam.training.ticketservice.database.model.ScreeningEntity;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.RoomRepository;
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
    RoomRepository roomRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ScreeningRepository repo;

    @ShellMethod(key = "create screening", value = "create screening")
    public String createScreening(String movieName, String roomName, String date) throws ParseException {
        var signedInPrivileged = isUserSignedInPrivileged();
        if (signedInPrivileged.isEmpty()) {
            Date parsed = Application.simpleDateFormat.parse(date);
            Optional<MovieEntity> optionalMovie = movieRepository.findMoviesEntityByName(movieName);
            Optional<RoomEntity> optionalRoom = roomRepository.findRoomsEntityByName(roomName);
            List<ScreeningEntity> inRoomList =
                    repo.findScreeningEntityByRoom(optionalRoom.orElse(null));
            if (!inRoomList.isEmpty()) {
                Instant instant = parsed.toInstant();
                Optional<ScreeningError> error =
                        inRoomList.stream()
                                .map(screeningEntity -> {
                                    var playtime = optionalMovie.map(MovieEntity::getPlaytime).orElse(0);
                                    return ScreeningError.getScreeningError(screeningEntity, playtime, instant);
                                }).filter(Predicate.not(ScreeningError.NONE::equals)).findFirst();
                if (error.isPresent()) {
                    switch (error.get()) {
                        case OVERLAPPING:
                            return "There is an overlapping screening";
                        case NO_BREAK:
                            return "This would start in the break"
                                    + " period after another screening in this room";
                        default:
                            break;
                    }
                }
            }
            repo.save(new ScreeningEntity(optionalMovie.orElse(null), optionalRoom.orElse(null), parsed));
            return null;
        }
        return signedInPrivileged.get();
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
