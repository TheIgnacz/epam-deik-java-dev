package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.model.RoomsEntity;
import com.epam.training.ticketservice.database.model.ScreeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<ScreeningEntity, Integer> {

    List<ScreeningEntity> findAll();

    List<ScreeningEntity> findScreeningEntityByRoom(RoomsEntity roomsEntity);

    Optional<ScreeningEntity> findScreeningEntityByMovie_NameAndRoom_NameAndDate(
            String movieName, String roomName, Date date);
}
