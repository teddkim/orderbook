/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.Trade;
import com.t4.orderbook.entities.User;
import java.util.List;

/**
 *
 * @author Owner
 */
public interface UserDao {
    
        
    /** 
     * Return a list of all users.
     * @param 
     * @return list of users
     */
    List<User> getAll();
    
    /** 
     * Return a user by id, or null if no user associated with that id
     * @param id
     * @return a single user object
     */
    User getUserById(int id);
    
    /** 
     * Create a new user
     * @param user
     * @return the added user
     */
    User addUser(User user);
    
    /** 
     * Deletes a user by id
     * @param id
     * @return nothing
     */
    boolean deleteUserById(int id);

}