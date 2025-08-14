package com.insta.backend.security;

import com.insta.backend.Model.Users;
import com.insta.backend.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServices implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users;
        users =userRepo.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(users);
    }
}
