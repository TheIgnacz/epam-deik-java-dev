package com.epam.training.ticketservice.services;

import com.epam.training.ticketservice.database.model.UserEntity;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class CliUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CliUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        var user = userRepository.findUsersEntityByName(userName);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Login failed due to incorrect credentials");
        }
        return new LoggedIn(user.get());
    }

    public static class LoggedIn implements UserDetails {

        private final UserEntity userEntity;

        public LoggedIn(UserEntity userEntity) {
            this.userEntity = userEntity;
        }

        public boolean isPrivileged() {
            return userEntity.isPrivileged();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return userEntity.getPw();
        }

        @Override
        public String getUsername() {
            return userEntity.getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
