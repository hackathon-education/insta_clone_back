package com.insta.backend.service;

import com.insta.backend.Model.Users;
import com.insta.backend.Repo.UserRepo;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users u = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.withUsername(u.getEmail())
                .password(u.getPassword())
                .build();
    }
}
