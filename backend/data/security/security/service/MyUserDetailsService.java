package com.javatech.security.service;

import com.javatech.security.entity.UserPrincipal;
import com.javatech.security.entity.Users;
import com.javatech.security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepo.findByUsername(username);

        if(user == null) {
            System.out.println("User Not Found!!");
            throw new UsernameNotFoundException("User Not Found!!");
        }

        return new UserPrincipal(user);
    }

}
