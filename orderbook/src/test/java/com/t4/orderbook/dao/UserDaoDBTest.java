/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Owner
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoDBTest {

    @Autowired
    UserDaoDB userDao;

    @Autowired
    OrderDaoDB orderDao;

    public UserDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Order> orders = orderDao.getAllOrders();
        for (Order order : orders) {
            orderDao.deleteOrderById(order.getId());
        }
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (user.getId() != 1) {
                userDao.deleteUserById(user.getId());
            }
        }
    }

    @AfterEach
    public void tearDown() {
        List<Order> orders = orderDao.getAllOrders();
        for (Order order : orders) {
            orderDao.deleteOrderById(order.getId());
        }
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (user.getId() != 1) {
                userDao.deleteUserById(user.getId());
            }
        }
    }

    /**
     * Test of getAll method, of class UserDaoDB.
     */
    @Test
    public void testGetAll() {
        User user = new User();
        user.setUsername("alibaba");
        user.setPassword("phantomthief");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        List<User> checkMe = userDao.getAll();
        assertEquals(2, checkMe.size());
        User user2 = new User();
        user2.setUsername("roxas");
        user2.setPassword("password1");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = userDao.addUser(user2);

        checkMe = userDao.getAll();
        assertEquals(3, checkMe.size());
    }

    /**
     * Test of getUserById method, of class UserDaoDB.
     */
    @Test
    public void testAddGetUserById() {
        User user = new User();
        user.setUsername("alibaba");
        user.setPassword("phantomthief");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);

        user = userDao.addUser(user);

        User fromDao = userDao.getUserById(user.getId());
        assertEquals(user, fromDao);
    }

    /**
     * Test of deleteUserById method, of class UserDaoDB.
     */
    @Test
    public void testDeleteUserById() {
        User user = new User();
        user.setUsername("alibaba");
        user.setPassword("phantomthief");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        List<User> checkMe = userDao.getAll();

        Order order = new Order();
        order.setUser(user);
        order.setAmount(BigDecimal.ONE);
        order.setPrice(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        order.setIsComplete(true);
        order.setIsSell(true);
        order = orderDao.addOrder(order);

        assertEquals(2, checkMe.size());
        userDao.deleteUserById(user.getId());
        checkMe = userDao.getAll();
        assertEquals(1, checkMe.size());

    }

}
