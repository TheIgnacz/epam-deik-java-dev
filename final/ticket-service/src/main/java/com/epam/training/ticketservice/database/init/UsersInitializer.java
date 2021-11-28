package com.epam.training.ticketservice.database.init;

import com.epam.training.ticketservice.database.model.UsersEntity;
import com.epam.training.ticketservice.database.repository.UsersRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UsersInitializer {
    private final UsersEntity adminUser;

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public UsersInitializer(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUser = new UsersEntity("admin", passwordEncoder.encode("admin"), true);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void init(ApplicationStartedEvent event) {
        var admin = usersRepository.findUsersEntityByName("admin");
        if (admin.isEmpty()) {
            usersRepository.save(adminUser);
        } else if (!admin.get().isPrivileged()) {
            admin.get().setPrivileged(true);
            usersRepository.save(admin.get());
        }
    }
}
