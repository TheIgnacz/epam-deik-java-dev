package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.model.RoomsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomsRepository extends JpaRepository<RoomsEntity, String> {

    List<RoomsEntity> findAll();

    Optional<RoomsEntity> findRoomsEntityByName(String name);
}
