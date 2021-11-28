package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.model.MoviesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MoviesEntity, String> {

    List<MoviesEntity> findAll();

    Optional<MoviesEntity> findMoviesEntityByName(String name);


}
