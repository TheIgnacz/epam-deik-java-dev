package com.epam.training.ticketservice.database.model;

import com.epam.training.ticketservice.Application;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class ScreeningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "name", nullable = false)
    private MoviesEntity movie;

    @ManyToOne
    @JoinColumn(referencedColumnName = "name", nullable = false)
    private RoomsEntity room;

    private Date date;

    public ScreeningEntity(MoviesEntity movie, RoomsEntity room, Date date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }

    public ScreeningEntity() {
    }

    public MoviesEntity getMovie() {
        return movie;
    }

    public void setMovie(MoviesEntity movie) {
        this.movie = movie;
    }

    public RoomsEntity getRoom() {
        return room;
    }

    public void setRoom(RoomsEntity room) {
        this.room = room;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%s, screened in room %s, at %s",
                movie.toString(), room.getName(), Application.simpleDateFormat.format(date));
    }
}
