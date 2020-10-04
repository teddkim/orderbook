package com.t4.orderbook.service;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.t4.orderbook.dao.UserDao;
import com.t4.orderbook.entities.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kennethan
 */
public class UserDaoStub implements UserDao {
    
    private List<User> users = new ArrayList<>();
    private int newId = 1;
    
    public UserDaoStub() {
//        User user = new User();
//        user.setId(1);
//        user.setUsername("user1");
//        user.setPassword("password1");
//        user.setCoin(BigDecimal.ONE);
//        user.setDollars(BigDecimal.ONE);
//        users.add(user);
//        
//        User user2 = new User();
//        user2.setId(2);
//        user2.setUsername("user2");
//        user2.setPassword("password2");
//        user2.setCoin(new BigDecimal("2"));
//        user2.setDollars(new BigDecimal("2"));
//        users.add(user2);
    }
    
    @Override
    public List<User> getAll() {
        return users;
    }
    
    @Override
    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    
    @Override
    public User addUser(User user) {
        user.setId(newId);
        users.add(user);
        newId++;
        return user;
    }
    
    @Override
    public boolean deleteUserById(int id) {
        User user = getUserById(id);
        if (user != null) {
            users.remove(user);
            return true;
        }
        return false;
    }
}
    