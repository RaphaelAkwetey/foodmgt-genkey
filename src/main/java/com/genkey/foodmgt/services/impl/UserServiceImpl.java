/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.model.impl.Users;

import com.genkey.foodmgt.repository.dao.api.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.genkey.foodmgt.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author david
 */
//@Named("userService")
@Service
@Slf4j
public class UserServiceImpl  implements UserService {

    @Autowired
    UserDAO repo;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Override
    public void updatePass(Users users, String username) {
        try {
            Users ExistingUser = repo.GetByUsername(username).orElse(null);
            String newPassword = passwordEncoder.encode(users.getPassword());
            ExistingUser.setPassword(newPassword);
            repo.save(ExistingUser);
            log.info("password change successful");
        }catch (Exception ex){
            log.error("Failed to change password " + ex.getMessage());
        }
    }

    @Override
    public Optional<List<Users>> getAllUsers() {
        return Optional.of(repo.findAll());
    }

    @Override
    public Optional<Users> loadUser(String id) {
        return repo.findById(id);
    }

    @Override
    public Users addUser(Users users) {
    String encodedPassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(encodedPassword);
        return repo.save(users);
    }
//Spring Boot, MySQL, JPA, Hibernate Restful CRUD API Example | Java Techie

    @Override
    public Users updateUsers(Users users,String id) {
        Users existingUser = repo.findById(id).orElse(null);
        existingUser.setFirstname(users.getFirstname());
        existingUser.setUsername(users.getUsername());
        existingUser.setEmail(users.getEmail());
        existingUser.setUserRoles(users.getUserRoles());
        existingUser.setLastname(users.getLastname());
        repo.save(existingUser);
        return existingUser;
    }

    @Transactional
    public boolean updatePassword(String newPassword,String username,String newUsername) {
        try {
            Users Existinguser = repo.GetByUsername(username).orElse(null);
            Existinguser.setPassword(passwordEncoder.encode(newPassword));
            Existinguser.setFirstTime(false);
            Existinguser.setUsername(newUsername);
            repo.save(Existinguser);
            log.info("password update successful " + newPassword);
            Date date = new Date();
            System.out.println(date);
            return true;
        } catch (Exception e) {
            log.error("user not found" + e.getMessage());
            return false;
        }

    }

    @Override
    public void deleteUser(String id) {
        repo.deleteById(id);
        System.out.println("user deleted");
    }
    @Override
    public String getIdByUsername(String username){
        Optional<Users> user = repo.GetByUsername(username);
        return user.get().getId();
    }

    @Override
    public void changePassword(Users user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        repo.save(user);
    }


    @Override
    public Optional<Users> getOne(String id){
        return repo.findById(id);
    }

}

  /*  @Inject
    @Named("userDAO")
    private UserDAOImpl userDao;

    public String getIdByUsername(String username) {
        return userDao.getIdByUsername(username);
    }

}*/
