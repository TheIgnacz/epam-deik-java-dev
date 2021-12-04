package com.epam.training.ticketservice.database.init;

import com.epam.training.ticketservice.database.model.UserEntity;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UsersInitializer {
    private final UserEntity adminUser;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UsersInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUser = new UserEntity("admin", passwordEncoder.encode("admin"), true);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void init(ApplicationStartedEvent event) {
        var admin = userRepository.findUsersEntityByName("admin");
        if (admin.isEmpty()) {
            userRepository.save(adminUser);
        } else if (!admin.get().isPrivileged()) {
            admin.get().setPrivileged(true);
            userRepository.save(admin.get());
        }
    }
}
