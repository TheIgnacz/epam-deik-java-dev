package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findUsersEntityByName(String name);
}
