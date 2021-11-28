package com.epam.training.ticketservice.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class MoviesEntity {

    @Id
    private String name;

    private String genre;
    private int playtime;

    @OneToMany(mappedBy = "movie")
    private List<ScreeningEntity> screeningEntities;

    public MoviesEntity(String name, String genre, int playtime) {
        this.name = name;
        this.genre = genre;
        this.playtime = playtime;
    }

    public MoviesEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String type) {
        this.genre = type;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    @Override
    public String toString() {
        return name + " (" + genre + ", " + playtime + " minutes)";
    }
}
