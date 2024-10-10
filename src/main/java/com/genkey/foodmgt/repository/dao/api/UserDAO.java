/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.Config.customUserDetails;
import com.genkey.foodmgt.dto.OrderResponse;
import com.genkey.foodmgt.model.impl.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author david
 */
@Repository
public interface UserDAO extends JpaRepository<Users,String> {



    List<Users> findUsersByEmail(String email);
    Optional<Users> findByEmailIgnoreCase(String email);

    @Query(value = "select * from users u where u.username = ?1", nativeQuery=true)
    Optional<Users> GetByUsername(String username);

    Optional<customUserDetails> findByUsername(String username);

    Users getOne(String id);


    @Query(value = "SELECT new com.genkey.foodmgt.dto.OrderResponse(u.firstname)" + "from Users u")
    List<OrderResponse> getFirstnameOfUsers();


    @Transactional
    @Modifying
    @Query(value = "UPDATE users " +
            "SET password = :newPassword " +
            "WHERE password = :oldPassword and username = :username ",nativeQuery = true)
    void changeUserPassword(String oldPassword, String newPassword, String username);

    @Query(value = "select * from users where firstname = :firstname ",nativeQuery = true)
    Users retrieveUsername(String firstname);

    @Query("select new com.genkey.foodmgt.dto.ChangePassword(u.password)" + " from Users u where u.username = :name ")
    String BringMeThePassword(String name);
}
