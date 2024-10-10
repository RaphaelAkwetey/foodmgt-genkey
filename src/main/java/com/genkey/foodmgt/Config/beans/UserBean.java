/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.Config.beans;

import com.genkey.foodmgt.model.impl.CreditLog;
import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.model.impl.UserGroup;
import com.genkey.foodmgt.model.impl.Users;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import com.genkey.foodmgt.services.api.UserService;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author david
 */
/*@Data
@Named
@Scope("session")
public class UserBean implements Serializable {

    @Inject
    UserService userService;

//    @PostConstruct
//    private void init() {
//    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        ServletContext servletContext = (ServletContext) externalContext.getContext();
//    WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
//                                   getAutowireCapableBeanFactory().
//                                   autowireBean(this);
//    }
// 
//    private void readObject(ObjectInputStream ois) 
//                            throws ClassNotFoundException, IOException {
//        ois.defaultReadObject();
//        init();
//    }
    private String id;
    private Date updated;
    private String updatedBy;
    private Date deletedDate;
    private String deletedBy;
    private String deletedReason;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
//    private boolean active;
    private int userGroup;
    private Set<Food_Order> orders;
    private CreditLog creditLog;
    private Map<String, Object> parameters;
    private int startRange;
    private int endRange;
    private final UserGroup USERGROUP1 = UserGroup.Admin;
    private final UserGroup USERGROUP2 = UserGroup.Fulltime;
    private final UserGroup USERGROUP3 = UserGroup.Intern;
    private String errorMsg;
    private String validationMsg;
//    private String var="hi";

//    public UserBean() {
//        id = 0;
//    }
//    public void create() {
//        Users user = new Users();
//        user.setActive(true);
//        user.setDeleted(false);
//        user.setFirstname(getFirstname());
//        user.setLastname(getLastname());
//        user.setUsername(getUsername());
//        user.setEmail(getEmail());
//        user.setPassword(getPassword());
//        user.setCredit(getCredit());
//        user.setCap(getCap());
//        user.setUserGroup(getUserGroup());
//        user.setOrders(getOrders());
//        user.setEmails(getEmails());
//        user.setCreditLog(getCreditLog());
//        userService.create(user);
//    }
    public void setActive(boolean active, Users user) {
        user.setActive(active);
        editActive(user);
    }

    public String create() {
        Users user = new Users();
        user.setActive(true);
        user.setDeleted(false);
        user.setFirstname(getFirstname());
        user.setLastname(getLastname());
        user.setUsername(getUsername());
        user.setEmail(getEmail());
        user.setPassword(getPassword());
        setUserGroup(getUserGroup(), user);
        userService.create(user);
//        reset();
        return "adminusers";
    }

    private void setUserGroup(int selection, Users user) {
        switch (selection) {
            case 1:
                user.setUserGroup(USERGROUP1);
                break;

            case 2:
                user.setUserGroup(USERGROUP2);
                break;

            case 3:
                user.setUserGroup(USERGROUP3);
                break;

            default:
                user.setUserGroup(USERGROUP2);
        }
    }

    public int count() {
        return userService.count();
    }

    public String destroy(String id) {
//        Users user = findById();
        Users user = userService.find(id);
        user.setDeleted(true);
        userService.destroy(user);
//        userService.find(getParameters());
        return "adminusers";
    }

    public Users findByParam() {
        Users user = userService.find(getParameters());
        reset();
        return user;
    }

    public void editActive(Users user) {
        user.setUpdated(new Date());
        if (userService.edit(user) != null) {
            setValidationMsg("User status changed");
        } else {
            setErrorMsg("Failed to change user status");
        }
    }

    public String edit(Users user) {
//        Users user = findById(id);
        user.setFirstname(getFirstname());
        user.setLastname(getLastname());
        user.setUsername(getUsername());
        setUserGroup(getUserGroup(), user);
        user.setEmail(getEmail());
        user.setPassword(getPassword());
        user.setUpdated(new Date());
        if (userService.edit(user) != null) {
            reset();
            setValidationMsg("User successfully edited");
            return "adminuser.xhtml";
        }else{
        setErrorMsg("Failed to edit user");
        return "edituser.xhtml";
    }
    }

    public void getIdByUsername(String username) {
        id = userService.getIdByUsername(username);
        if (id.length() > 0) {
            setId(id);
        }
    }

    public Users find(String id) {
        return userService.find(id);
//        reset();
    }

    public List<Users> findAll() {
        return userService.findAll();
    }

    public List<Users> findRange() {
        List<Users> users = userService.findRange(getStartRange(), getEndRange());
        reset();
        return users;
    }

    public void reset() {
        id = "";
        updated = null;
        updatedBy = null;
        deletedDate = null;
        deletedBy = null;
        deletedReason = null;
        firstname = null;
        lastname = null;
        username = null;
        email = null;
        password = null;
        userGroup = 0;
        orders = null;
        creditLog = null;
        parameters = null;
        startRange = 0;
        endRange = 0;
        errorMsg = null;
    }

    public List matchUserstoValue() {
        List users = findAll();
        List mappedUsers = null;
        UserValuePair pair;
        for (Object user : users) {
            pair = new UserValuePair((Users) user, users.indexOf(user));
            mappedUsers.add(pair);
        }
        return mappedUsers;
    }

    public static class UserValuePair {

        private String label;
        private Integer value;

        public UserValuePair(Users user, Integer val) {
            label = user.getFirstname() + " " + user.getLastname();
            value = val;
        }
    }

    public String transitionToEdit(String id) {
        setId(id);
        return "edituser.xhtml";
    }
}*/
