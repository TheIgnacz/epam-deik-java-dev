package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findUsersEntityByName(String name);
}
