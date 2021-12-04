package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.model.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {

    List<RoomEntity> findAll();

    Optional<RoomEntity> findRoomsEntityByName(String name);
}
