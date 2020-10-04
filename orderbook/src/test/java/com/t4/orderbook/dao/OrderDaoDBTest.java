/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author kennethan
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoDBTest {

    @Autowired
    OrderDao orderDao;

    @Autowired
    TradeDao tradeDao;

    @Autowired
    UserDao userDao;

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

    @Test
    public void testAddGetOrder() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = orderDao.addOrder(order);

        Order fromDao = orderDao.getOrderById(order.getId());
        assertEquals(order, fromDao);
    }

    @Test
    public void testGetAllOrder() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = orderDao.addOrder(order2);

        List<Order> orders = orderDao.getAllOrders();
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order));
        assertTrue(orders.contains(order2));
    }

    @Test
    public void testUpdateOrder() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = orderDao.addOrder(order);

        Order fromDao = orderDao.getOrderById(order.getId());
        assertEquals(order, fromDao);

        order.setPrice(new BigDecimal("2"));
        orderDao.updateOrder(order);
        assertNotEquals(order, fromDao);

        fromDao = orderDao.getOrderById(order.getId());
        assertEquals(order, fromDao);
    }

    @Test
    public void testDeleteOrder() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = orderDao.addOrder(order);

        orderDao.deleteOrderById(order.getId());

        Order fromDao = orderDao.getOrderById(order.getId());
        assertNull(fromDao);
    }

    @Test
    public void testGetOrderByUser() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = userDao.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = orderDao.addOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = orderDao.addOrder(order3);

        List<Order> orders = orderDao.getOrdersByUser(user);
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order));
        assertTrue(orders.contains(order2));
        assertFalse(orders.contains(order3));
    }

    @Test
    public void testGetOrdersByDate() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = userDao.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = orderDao.addOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = orderDao.addOrder(order3);

        List<Order> orders = orderDao.getOrdersByDate(
                LocalDateTime.of(2020, Month.SEPTEMBER, 16, 0, 0, 0),
                LocalDateTime.of(2020, Month.SEPTEMBER, 17, 23, 59, 59));

        assertEquals(2, orders.size());
        assertTrue(orders.contains(order2));
        assertTrue(orders.contains(order3));
        assertFalse(orders.contains(order));
    }

    @Test
    public void testGetOrdersByDateAndUser() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = userDao.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = orderDao.addOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = orderDao.addOrder(order3);

        List<Order> orders = orderDao.getOrdersByDate(
                LocalDateTime.of(2020, Month.SEPTEMBER, 16, 0, 0, 0),
                LocalDateTime.of(2020, Month.SEPTEMBER, 17, 23, 59, 59),
                user);

        assertEquals(1, orders.size());
        assertTrue(orders.contains(order2));
        assertFalse(orders.contains(order));
        assertFalse(orders.contains(order3));
    }

    @Test
    public void testGetOrdersByType() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = userDao.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = orderDao.addOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = orderDao.addOrder(order3);

        List<Order> orders = orderDao.getOrdersByType(true);
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order2));
        assertTrue(orders.contains(order3));
        assertFalse(orders.contains(order));
    }

    @Test
    public void testGetOrdersByTypeAndUser() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = userDao.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = orderDao.addOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = orderDao.addOrder(order3);

        List<Order> orders = orderDao.getOrdersByType(true, user);
        assertEquals(1, orders.size());
        assertTrue(orders.contains(order2));
        assertFalse(orders.contains(order3));
        assertFalse(orders.contains(order));
    }

    @Test
    public void testGetOrdersByCompletion() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = userDao.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = userDao.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = orderDao.addOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(true);
        order2 = orderDao.addOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(true);
        order3 = orderDao.addOrder(order3);

        List<Order> orders = orderDao.getOrdersByCompletion(true, user);
        assertEquals(1, orders.size());
        assertTrue(orders.contains(order2));
        assertFalse(orders.contains(order));
        assertFalse(orders.contains(order3));
    }
}
