package com.genkey.foodmgt.Config;

import com.genkey.foodmgt.model.impl.UserRoles;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.repository.dao.api.UserDAO;
import com.genkey.foodmgt.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(value = 3)
@Component
public class DefaultLoginCred implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;



    @Override
    public void run(String... args) throws Exception {


       try {
           List<Users> exUser = userDAO.findAll();
           if (exUser.isEmpty()) {
               System.out.println("user not found");
               Users users = new Users();
               users.setFirstname("Admin");
               users.setLastname("Admin");
               users.setUsername("Admin1");
               users.setEmail("Admin@gmail.com");
               users.setUserRoles(UserRoles.ADMIN);
               users.setPassword("admin123");
               userService.addUser(users);
           } else {
               for (Users u : exUser) {
                   if (u.getUsername().equals("Admin1")) {
                       System.out.println("user exist already");
                   }


                }
              }


       }catch(Exception e){
           System.out.println("there's already an existing user");
       }
       }



}
