/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.User;
import com.t4.orderbook.entities.Trade;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
 * @author AmanK
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeDaoDBTest {

    @Autowired
    UserDaoDB userDao;

    @Autowired
    OrderDaoDB orderDao;

    @Autowired
    TradeDaoDB tradeDao;

    public TradeDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Trade> trades = tradeDao.getAllTrades();
        for (Trade trade : trades) {
            tradeDao.deleteTradeById(trade.getId());
        }
        
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
        List<Trade> trades = tradeDao.getAllTrades();
        for (Trade trade : trades) {
            tradeDao.deleteTradeById(trade.getId());
        }
        
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
     * Test of getTradeById method, of class TradeDaoDB.
     */
    @Test
    public void testAddTradeAndGetTradeById() {
        User buyUser = new User();
        buyUser.setUsername("testBuyer");
        buyUser.setPassword("testBuyer");
        buyUser.setCoin(new BigDecimal("10"));
        buyUser.setDollars(new BigDecimal("10000"));
        buyUser = userDao.addUser(buyUser);

        User sellUser = new User();
        sellUser.setUsername("testSeller");
        sellUser.setPassword("testSeller");
        sellUser.setCoin(new BigDecimal("200"));
        sellUser.setDollars(new BigDecimal("9990"));
        sellUser = userDao.addUser(sellUser);

        Order buyOrder = new Order();
        buyOrder.setDateTime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder.setAmount(new BigDecimal("3.202"));
        buyOrder.setPrice(new BigDecimal("400.23232"));
        buyOrder.setIsComplete(true);
        buyOrder.setIsSell(false);
        buyOrder.setUser(buyUser);
        buyOrder = orderDao.addOrder(buyOrder);

        Order sellOrder = new Order();
        sellOrder.setDateTime(LocalDateTime.parse("15-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder.setAmount(new BigDecimal("3.202"));
        sellOrder.setPrice(new BigDecimal("400.23232"));
        sellOrder.setIsComplete(true);
        sellOrder.setIsSell(true);
        sellOrder.setUser(sellUser);
        sellOrder = orderDao.addOrder(sellOrder);

        Trade trade = new Trade();
        trade.setDatetime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade.setAmount(new BigDecimal("3.202"));
        trade.setPrice(new BigDecimal("400.23232"));
        trade.setBuyOrder(buyOrder);
        trade.setSellOrder(sellOrder);

        Trade addedTrade = tradeDao.addTrade(trade);

        Trade retrievedTrade = tradeDao.getTradeById(trade.getId());
        
        assertEquals(retrievedTrade, addedTrade);

    }

    /**
     * Test of getAllTrades method, of class TradeDaoDB.
     */
    @Test
    public void testGetAllTradesAndGetAllTradesForUser() {
        
        List<Trade> addedTrades = new ArrayList<>();
        
        User user1 = new User();
        user1.setUsername("testBuyer");
        user1.setPassword("testBuyer");
        user1.setCoin(new BigDecimal("10"));
        user1.setDollars(new BigDecimal("10000"));
        user1 = userDao.addUser(user1);

        User user2 = new User();
        user2.setUsername("testSeller");
        user2.setPassword("testSeller");
        user2.setCoin(new BigDecimal("200"));
        user2.setDollars(new BigDecimal("9990"));
        user2 = userDao.addUser(user2);
        
        User user3 = new User();
        user3.setUsername("testDude");
        user3.setPassword("testDude");
        user3.setCoin(new BigDecimal("200"));
        user3.setDollars(new BigDecimal("9990"));
        user3 = userDao.addUser(user3);
        
        Order buyOrder1 = new Order();
        buyOrder1.setDateTime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder1.setAmount(new BigDecimal("3.202"));
        buyOrder1.setPrice(new BigDecimal("400.23232"));
        buyOrder1.setIsComplete(true);
        buyOrder1.setIsSell(false);
        buyOrder1.setUser(user1);
        buyOrder1 = orderDao.addOrder(buyOrder1);

        Order sellOrder1 = new Order();
        sellOrder1.setDateTime(LocalDateTime.parse("15-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder1.setAmount(new BigDecimal("3.202"));
        sellOrder1.setPrice(new BigDecimal("400.23232"));
        sellOrder1.setIsComplete(true);
        sellOrder1.setIsSell(true);
        sellOrder1.setUser(user2);
        sellOrder1 = orderDao.addOrder(sellOrder1);
        
        Trade trade1 = new Trade();
        trade1.setDatetime(LocalDateTime.parse("15-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade1.setAmount(new BigDecimal("3.202"));
        trade1.setPrice(new BigDecimal("400.23232"));
        trade1.setBuyOrder(buyOrder1);
        trade1.setSellOrder(sellOrder1);
        Trade addedTrade1 = tradeDao.addTrade(trade1);
        
        addedTrades.add(addedTrade1);
              
        Order buyOrder2 = new Order();
        buyOrder2.setDateTime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder2.setAmount(new BigDecimal("67.02"));
        buyOrder2.setPrice(new BigDecimal("500.23232"));
        buyOrder2.setIsComplete(true);
        buyOrder2.setIsSell(false);
        buyOrder2.setUser(user1);
        buyOrder2 = orderDao.addOrder(buyOrder2);

        Order sellOrder2 = new Order();
        sellOrder2.setDateTime(LocalDateTime.parse("05-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder2.setAmount(new BigDecimal("67.02"));
        sellOrder2.setPrice(new BigDecimal("500.23232"));
        sellOrder2.setIsComplete(true);
        sellOrder2.setIsSell(true);
        sellOrder2.setUser(user3);
        sellOrder2 = orderDao.addOrder(sellOrder2);
        
        Trade trade2 = new Trade();
        trade2.setDatetime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade2.setAmount(new BigDecimal("67.02"));
        trade2.setPrice(new BigDecimal("500.23232"));
        trade2.setBuyOrder(buyOrder2);
        trade2.setSellOrder(sellOrder2);
        Trade addedTrade2 = tradeDao.addTrade(trade2);
        
        addedTrades.add(addedTrade2);
        
        List<Trade> allRetrievedTrades = tradeDao.getAllTrades();

        assertEquals(2,allRetrievedTrades.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        assertTrue(allRetrievedTrades.contains(addedTrade2));
        
                
        List<Trade> allRetrievedTradesForUser1 = tradeDao.getAllTradesForUser(user1);
        assertEquals(2,allRetrievedTradesForUser1.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        assertTrue(allRetrievedTrades.contains(addedTrade2));
        
        List<Trade> allRetrievedTradesForUser2 = tradeDao.getAllTradesForUser(user2);
        assertEquals(1,allRetrievedTradesForUser2.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        
        List<Trade> allRetrievedTradesForUser3 = tradeDao.getAllTradesForUser(user3);
        assertEquals(1,allRetrievedTradesForUser3.size());
        assertTrue(allRetrievedTrades.contains(addedTrade2));
        
    }

    /**
     * Test of deleteTradeById method, of class TradeDaoDB.
     */
    @Test
    public void testDeleteTradeById() {
    }

    /**
     * Test of updateTrade method, of class TradeDaoDB.
     */
    @Test
    public void testUpdateTrade() {
        User buyUser = new User();
        buyUser.setUsername("testBuyer");
        buyUser.setPassword("testBuyer");
        buyUser.setCoin(new BigDecimal("10"));
        buyUser.setDollars(new BigDecimal("10000"));
        buyUser = userDao.addUser(buyUser);

        User sellUser = new User();
        sellUser.setUsername("testSeller");
        sellUser.setPassword("testSeller");
        sellUser.setCoin(new BigDecimal("200"));
        sellUser.setDollars(new BigDecimal("9990"));
        sellUser = userDao.addUser(sellUser);

        Order buyOrder = new Order();
        buyOrder.setDateTime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder.setAmount(new BigDecimal("3.202"));
        buyOrder.setPrice(new BigDecimal("400.23232"));
        buyOrder.setIsComplete(true);
        buyOrder.setIsSell(false);
        buyOrder.setUser(buyUser);
        buyOrder = orderDao.addOrder(buyOrder);

        Order sellOrder = new Order();
        sellOrder.setDateTime(LocalDateTime.parse("15-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder.setAmount(new BigDecimal("3.202"));
        sellOrder.setPrice(new BigDecimal("400.23232"));
        sellOrder.setIsComplete(true);
        sellOrder.setIsSell(true);
        sellOrder.setUser(sellUser);
        sellOrder = orderDao.addOrder(sellOrder);

        Trade trade = new Trade();
        trade.setDatetime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade.setAmount(new BigDecimal("3.20"));
        trade.setPrice(new BigDecimal("400.23232"));
        trade.setBuyOrder(buyOrder);
        trade.setSellOrder(sellOrder);
        trade = tradeDao.addTrade(trade);
                     
        trade.setPrice(new BigDecimal("420.241"));
        trade.setAmount(new BigDecimal("2.241"));
        trade.setDatetime(LocalDateTime.parse("15-09-2020 12:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        tradeDao.updateTrade(trade);
        Trade retrievedTradeAfterUpdate = tradeDao.getTradeById(trade.getId());
 
        assertEquals(retrievedTradeAfterUpdate, trade);          
        
        
    }

    /**
     * Test of getTradesByDateRange method, of class TradeDaoDB.
     */
    @Test
    public void testGetTradesByDateRangeAndUser() {
        User user1 = new User();
        user1.setUsername("testBuyer");
        user1.setPassword("testBuyer");
        user1.setCoin(new BigDecimal("10"));
        user1.setDollars(new BigDecimal("10000"));
        user1 = userDao.addUser(user1);

        User user2 = new User();
        user2.setUsername("testSeller");
        user2.setPassword("testSeller");
        user2.setCoin(new BigDecimal("200"));
        user2.setDollars(new BigDecimal("9990"));
        user2 = userDao.addUser(user2);
        
        User user3 = new User();
        user3.setUsername("testDude");
        user3.setPassword("testDude");
        user3.setCoin(new BigDecimal("200"));
        user3.setDollars(new BigDecimal("9990"));
        user3 = userDao.addUser(user3);
        
        Order buyOrder1 = new Order();
        buyOrder1.setDateTime(LocalDateTime.parse("10-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder1.setAmount(new BigDecimal("3.202"));
        buyOrder1.setPrice(new BigDecimal("400.23232"));
        buyOrder1.setIsComplete(true);
        buyOrder1.setIsSell(false);
        buyOrder1.setUser(user1);
        buyOrder1 = orderDao.addOrder(buyOrder1);

        Order sellOrder1 = new Order();
        sellOrder1.setDateTime(LocalDateTime.parse("10-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder1.setAmount(new BigDecimal("3.202"));
        sellOrder1.setPrice(new BigDecimal("400.23232"));
        sellOrder1.setIsComplete(true);
        sellOrder1.setIsSell(true);
        sellOrder1.setUser(user2);
        sellOrder1 = orderDao.addOrder(sellOrder1);
        
        Trade trade1 = new Trade();
        trade1.setDatetime(LocalDateTime.parse("10-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade1.setAmount(new BigDecimal("3.202"));
        trade1.setPrice(new BigDecimal("400.23232"));
        trade1.setBuyOrder(buyOrder1);
        trade1.setSellOrder(sellOrder1);
        Trade addedTrade1 = tradeDao.addTrade(trade1);
        
              
        Order buyOrder2 = new Order();
        buyOrder2.setDateTime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder2.setAmount(new BigDecimal("67.02"));
        buyOrder2.setPrice(new BigDecimal("500.23232"));
        buyOrder2.setIsComplete(true);
        buyOrder2.setIsSell(false);
        buyOrder2.setUser(user1);
        buyOrder2 = orderDao.addOrder(buyOrder2);

        Order sellOrder2 = new Order();
        sellOrder2.setDateTime(LocalDateTime.parse("05-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder2.setAmount(new BigDecimal("67.02"));
        sellOrder2.setPrice(new BigDecimal("500.23232"));
        sellOrder2.setIsComplete(true);
        sellOrder2.setIsSell(true);
        sellOrder2.setUser(user3);
        sellOrder2 = orderDao.addOrder(sellOrder2);
        
        Trade trade2 = new Trade();
        trade2.setDatetime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade2.setAmount(new BigDecimal("67.02"));
        trade2.setPrice(new BigDecimal("500.23232"));
        trade2.setBuyOrder(buyOrder2);
        trade2.setSellOrder(sellOrder2);
        Trade addedTrade2 = tradeDao.addTrade(trade2);
        
        
        List<Trade> allRetrievedTrades = tradeDao.getTradesByDateRange(LocalDateTime.of(2020, 9, 4, 12, 0, 0), LocalDateTime.of(2020, 9, 11, 12, 0, 0));
        assertEquals(2,allRetrievedTrades.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        assertTrue(allRetrievedTrades.contains(addedTrade2));
         
        List<Trade> RetrievedTradesForRange1 = tradeDao.getTradesByDateRange(LocalDateTime.of(2020, 9, 9, 12, 0, 0), LocalDateTime.of(2020, 9, 11, 12, 0, 0));
        assertEquals(1,RetrievedTradesForRange1.size());
        assertTrue(RetrievedTradesForRange1.contains(addedTrade1));
        
        List<Trade> RetrievedTradesForRange2 = tradeDao.getTradesByDateRange(LocalDateTime.of(2020, 9, 4, 12, 0, 0), LocalDateTime.of(2020, 9, 6, 12, 0, 0));
        assertEquals(1,RetrievedTradesForRange2.size());
        assertTrue(RetrievedTradesForRange2.contains(addedTrade2));
        
        List<Trade> RetrievedTradesForRange0User1 = tradeDao.getTradesByDateRangeAndUser(LocalDateTime.of(2020, 9, 4, 12, 0, 0), LocalDateTime.of(2020, 9, 11, 12, 0, 0), user1);
        assertEquals(2,RetrievedTradesForRange0User1.size());
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade1));
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade2));
        
        List<Trade> RetrievedTradesForRange1User1 = tradeDao.getTradesByDateRangeAndUser(LocalDateTime.of(2020, 9, 9, 12, 0, 0), LocalDateTime.of(2020, 9, 11, 12, 0, 0), user1);
        assertEquals(1,RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));
        
        List<Trade> RetrievedTradesForRange1User2 = tradeDao.getTradesByDateRangeAndUser(LocalDateTime.of(2020, 9, 9, 12, 0, 0), LocalDateTime.of(2020, 9, 11, 12, 0, 0), user2);
        assertEquals(1,RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));
    }


    /**
     * Test of getTradesByPriceRangeAndUser method, of class TradeDaoDB.
     */
    @Test
    public void testGetTradesByPriceRangeAndUser() {
        User user1 = new User();
        user1.setUsername("testBuyer");
        user1.setPassword("testBuyer");
        user1.setCoin(new BigDecimal("10"));
        user1.setDollars(new BigDecimal("10000"));
        user1 = userDao.addUser(user1);

        User user2 = new User();
        user2.setUsername("testSeller");
        user2.setPassword("testSeller");
        user2.setCoin(new BigDecimal("200"));
        user2.setDollars(new BigDecimal("9990"));
        user2 = userDao.addUser(user2);
        
        User user3 = new User();
        user3.setUsername("testDude");
        user3.setPassword("testDude");
        user3.setCoin(new BigDecimal("200"));
        user3.setDollars(new BigDecimal("9990"));
        user3 = userDao.addUser(user3);
        
        Order buyOrder1 = new Order();
        buyOrder1.setDateTime(LocalDateTime.parse("10-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder1.setAmount(new BigDecimal("3.202"));
        buyOrder1.setPrice(new BigDecimal("400.23232"));
        buyOrder1.setIsComplete(true);
        buyOrder1.setIsSell(false);
        buyOrder1.setUser(user1);
        buyOrder1 = orderDao.addOrder(buyOrder1);

        Order sellOrder1 = new Order();
        sellOrder1.setDateTime(LocalDateTime.parse("10-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder1.setAmount(new BigDecimal("3.202"));
        sellOrder1.setPrice(new BigDecimal("400.23232"));
        sellOrder1.setIsComplete(true);
        sellOrder1.setIsSell(true);
        sellOrder1.setUser(user2);
        sellOrder1 = orderDao.addOrder(sellOrder1);
        
        Trade trade1 = new Trade();
        trade1.setDatetime(LocalDateTime.parse("10-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade1.setAmount(new BigDecimal("3.202"));
        trade1.setPrice(new BigDecimal("400.23232"));
        trade1.setBuyOrder(buyOrder1);
        trade1.setSellOrder(sellOrder1);
        Trade addedTrade1 = tradeDao.addTrade(trade1);
        
              
        Order buyOrder2 = new Order();
        buyOrder2.setDateTime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder2.setAmount(new BigDecimal("67.02"));
        buyOrder2.setPrice(new BigDecimal("500.23232"));
        buyOrder2.setIsComplete(true);
        buyOrder2.setIsSell(false);
        buyOrder2.setUser(user1);
        buyOrder2 = orderDao.addOrder(buyOrder2);

        Order sellOrder2 = new Order();
        sellOrder2.setDateTime(LocalDateTime.parse("05-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder2.setAmount(new BigDecimal("67.02"));
        sellOrder2.setPrice(new BigDecimal("500.23232"));
        sellOrder2.setIsComplete(true);
        sellOrder2.setIsSell(true);
        sellOrder2.setUser(user3);
        sellOrder2 = orderDao.addOrder(sellOrder2);
        
        Trade trade2 = new Trade();
        trade2.setDatetime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade2.setAmount(new BigDecimal("67.02"));
        trade2.setPrice(new BigDecimal("500.23232"));
        trade2.setBuyOrder(buyOrder2);
        trade2.setSellOrder(sellOrder2);
        Trade addedTrade2 = tradeDao.addTrade(trade2);
        
        
        List<Trade> allRetrievedTrades = tradeDao.getTradesByPriceRange(new BigDecimal("350"), new BigDecimal("550"));
        assertEquals(2,allRetrievedTrades.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        assertTrue(allRetrievedTrades.contains(addedTrade2));
         
        List<Trade> RetrievedTradesForRange1 = tradeDao.getTradesByPriceRange(new BigDecimal("350"), new BigDecimal("450"));
        assertEquals(1,RetrievedTradesForRange1.size());
        assertTrue(RetrievedTradesForRange1.contains(addedTrade1));
        
        List<Trade> RetrievedTradesForRange2 = tradeDao.getTradesByPriceRange(new BigDecimal("450"), new BigDecimal("550"));
        assertEquals(1,RetrievedTradesForRange2.size());
        assertTrue(RetrievedTradesForRange2.contains(addedTrade2));
        
        List<Trade> RetrievedTradesForRange0User1 = tradeDao.getTradesByPriceRangeAndUser(new BigDecimal("350"), new BigDecimal("550"), user1);
        assertEquals(2,RetrievedTradesForRange0User1.size());
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade1));
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade2));
        
        List<Trade> RetrievedTradesForRange1User1 = tradeDao.getTradesByPriceRangeAndUser(new BigDecimal("350"), new BigDecimal("450"), user1);
        assertEquals(1,RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));
        
        List<Trade> RetrievedTradesForRange1User2 = tradeDao.getTradesByPriceRangeAndUser(new BigDecimal("350"), new BigDecimal("450"), user2);
        assertEquals(1,RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));
    }

    /**
     * Test of getTradesByAmountRangeAndUser method, of class TradeDaoDB.
     */
    @Test
    public void testGetTradesByAmountRangeAndUser() {
        User user1 = new User();
        user1.setUsername("testBuyer");
        user1.setPassword("testBuyer");
        user1.setCoin(new BigDecimal("10"));
        user1.setDollars(new BigDecimal("10000"));
        user1 = userDao.addUser(user1);

        User user2 = new User();
        user2.setUsername("testSeller");
        user2.setPassword("testSeller");
        user2.setCoin(new BigDecimal("200"));
        user2.setDollars(new BigDecimal("9990"));
        user2 = userDao.addUser(user2);
        
        User user3 = new User();
        user3.setUsername("testDude");
        user3.setPassword("testDude");
        user3.setCoin(new BigDecimal("200"));
        user3.setDollars(new BigDecimal("9990"));
        user3 = userDao.addUser(user3);
        
        Order buyOrder1 = new Order();
        buyOrder1.setDateTime(LocalDateTime.parse("10-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder1.setAmount(new BigDecimal("3.202"));
        buyOrder1.setPrice(new BigDecimal("400.23232"));
        buyOrder1.setIsComplete(true);
        buyOrder1.setIsSell(false);
        buyOrder1.setUser(user1);
        buyOrder1 = orderDao.addOrder(buyOrder1);

        Order sellOrder1 = new Order();
        sellOrder1.setDateTime(LocalDateTime.parse("10-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder1.setAmount(new BigDecimal("3.202"));
        sellOrder1.setPrice(new BigDecimal("400.23232"));
        sellOrder1.setIsComplete(true);
        sellOrder1.setIsSell(true);
        sellOrder1.setUser(user2);
        sellOrder1 = orderDao.addOrder(sellOrder1);
        
        Trade trade1 = new Trade();
        trade1.setDatetime(LocalDateTime.parse("10-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade1.setAmount(new BigDecimal("3.202"));
        trade1.setPrice(new BigDecimal("400.23232"));
        trade1.setBuyOrder(buyOrder1);
        trade1.setSellOrder(sellOrder1);
        Trade addedTrade1 = tradeDao.addTrade(trade1);
        
              
        Order buyOrder2 = new Order();
        buyOrder2.setDateTime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder2.setAmount(new BigDecimal("67.02"));
        buyOrder2.setPrice(new BigDecimal("500.23232"));
        buyOrder2.setIsComplete(true);
        buyOrder2.setIsSell(false);
        buyOrder2.setUser(user1);
        buyOrder2 = orderDao.addOrder(buyOrder2);

        Order sellOrder2 = new Order();
        sellOrder2.setDateTime(LocalDateTime.parse("05-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder2.setAmount(new BigDecimal("67.02"));
        sellOrder2.setPrice(new BigDecimal("500.23232"));
        sellOrder2.setIsComplete(true);
        sellOrder2.setIsSell(true);
        sellOrder2.setUser(user3);
        sellOrder2 = orderDao.addOrder(sellOrder2);
        
        Trade trade2 = new Trade();
        trade2.setDatetime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade2.setAmount(new BigDecimal("67.02"));
        trade2.setPrice(new BigDecimal("500.23232"));
        trade2.setBuyOrder(buyOrder2);
        trade2.setSellOrder(sellOrder2);
        Trade addedTrade2 = tradeDao.addTrade(trade2);
        
        
        List<Trade> allRetrievedTrades = tradeDao.getTradesByAmountRange(new BigDecimal("2"), new BigDecimal("100"));
        assertEquals(2,allRetrievedTrades.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        assertTrue(allRetrievedTrades.contains(addedTrade2));
         
        List<Trade> RetrievedTradesForRange1 = tradeDao.getTradesByAmountRange(new BigDecimal("2"), new BigDecimal("10"));
        assertEquals(1,RetrievedTradesForRange1.size());
        assertTrue(RetrievedTradesForRange1.contains(addedTrade1));
        
        List<Trade> RetrievedTradesForRange2 = tradeDao.getTradesByAmountRange(new BigDecimal("10"), new BigDecimal("100"));
        assertEquals(1,RetrievedTradesForRange2.size());
        assertTrue(RetrievedTradesForRange2.contains(addedTrade2));
        
        List<Trade> RetrievedTradesForRange0User1 = tradeDao.getTradesByAmountRangeAndUser(new BigDecimal("2"), new BigDecimal("100"), user1);
        assertEquals(2,RetrievedTradesForRange0User1.size());
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade1));
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade2));
        
        List<Trade> RetrievedTradesForRange1User1 = tradeDao.getTradesByAmountRangeAndUser(new BigDecimal("2"), new BigDecimal("10"), user1);
        assertEquals(1,RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));
        
        List<Trade> RetrievedTradesForRange1User2 = tradeDao.getTradesByAmountRangeAndUser(new BigDecimal("2"), new BigDecimal("10"), user2);
        assertEquals(1,RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));
    }

}
