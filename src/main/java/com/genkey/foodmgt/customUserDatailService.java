package com.genkey.foodmgt;

import com.genkey.foodmgt.Config.customUserDetails;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.repository.dao.api.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class customUserDatailService implements UserDetailsService {


    @Autowired
    private UserDAO userRepo;

    private final static String USER_NOT_FOUND
            = "user with username %s not found";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
    }
}
