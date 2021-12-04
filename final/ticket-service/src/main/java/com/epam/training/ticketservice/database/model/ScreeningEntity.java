package com.epam.training.ticketservice.database.model;

import com.epam.training.ticketservice.Application;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

@Entity
public class ScreeningEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "name", nullable = false)
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(referencedColumnName = "name", nullable = false)
    private RoomEntity room;

    private Date date;

    public ScreeningEntity(MovieEntity movie, RoomEntity room, Date date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }

    public ScreeningEntity() {
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScreeningEntity)) {
            return false;
        }
        ScreeningEntity that = (ScreeningEntity) o;
        return getMovie().equals(that.getMovie())
                && getRoom().equals(that.getRoom())
                && getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovie(), getRoom(), getDate());
    }
}
