package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String> {

    List<MovieEntity> findAll();

    Optional<MovieEntity> findMoviesEntityByName(String name);


}
