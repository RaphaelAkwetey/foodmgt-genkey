/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.api;

import com.genkey.foodmgt.model.impl.Users;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author david
 */
public interface UserService  {

    void updatePass(Users users,String username);
    public Optional<List<Users>> getAllUsers();

    public Optional<Users> loadUser(String id);

    public Users addUser(Users users);

    public Users updateUsers(Users users,String id) ;

    public void deleteUser(String id);
    String getIdByUsername(String username);

    void changePassword(Users user, String newPassword);

    Optional<Users> getOne(String id);
}
