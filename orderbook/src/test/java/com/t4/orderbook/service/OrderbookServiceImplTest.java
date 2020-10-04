/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.service;

import com.t4.orderbook.dao.OpeningPricesDao;
import com.t4.orderbook.dao.OrderDao;
import com.t4.orderbook.dao.ShareDao;
import com.t4.orderbook.dao.TradeDao;
import com.t4.orderbook.dao.UserDao;
import com.t4.orderbook.entities.OpeningPrices;
import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.Share;
import com.t4.orderbook.entities.Trade;
import com.t4.orderbook.entities.User;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author AmanK
 */
public class OrderbookServiceImplTest {

    UserDao userDao = new UserDaoStub();

    OrderDao orderDao = new OrderDaoStub();

    TradeDao tradeDao = new TradeDaoStub();

    ShareDao shareDao = new ShareDaoStub();

    OpeningPricesDao openingPricesDao = new OpeningPricesDaoStub();

    OrderbookService service = new OrderbookServiceImpl(userDao, orderDao, tradeDao, shareDao, openingPricesDao);

    public OrderbookServiceImplTest() {
    }

    @BeforeEach
    public void setUp() {
        //userDao = new UserDaoStub();

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

        List<Share> shares = shareDao.getAllShares();
        for (Share share : shares) {
            shareDao.deleteShare(share.getId());
        }

        List<OpeningPrices> openingPricesList = openingPricesDao.getAllOpeningPrices();
        for (OpeningPrices thisOpeningPrice : openingPricesList) {
            openingPricesDao.deleteOpeningPrices(thisOpeningPrice.getDatetime(), thisOpeningPrice.getShareId());
        }

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCreateOrderAndGetOrderById() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);

        order = service.createOrder(order);

        Order fromDao = service.getOrderById(order.getId());
        assertEquals(order, fromDao);
    }

    @Test
    public void testUpdateOrder() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order fromDao = service.getOrderById(order.getId());
        assertEquals(order, fromDao);

        order.setPrice(new BigDecimal("2"));
        service.updateOrder(order);
        assertNotEquals(order, fromDao);

        fromDao = service.getOrderById(order.getId());

        assertEquals(order, fromDao);
    }

    @Test
    public void testDeleteOrder() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        service.deleteOrder(order);

        Order fromDao = service.getOrderById(order.getId());
        assertNull(fromDao);
    }

    @Test
    public void testGetAskPrice() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = service.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = service.createOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = service.createOrder(order3);

//        BigDecimal askPrice = service.getAskPrice();
//        assertEquals(askPrice,order2.getPrice());
    }

    @Test
    public void testGetBidPrice() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = service.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = service.createOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(false);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = service.createOrder(order3);

//        BigDecimal bidPrice = service.getBidPrice();
//        assertEquals(bidPrice,order3.getPrice());
    }

    @Test
    public void testCreateTradeSameAmountAndGetTradeById() {
        User buyUser = new User();
        buyUser.setUsername("testBuyer");
        buyUser.setPassword("testBuyer");
        buyUser.setCoin(new BigDecimal("10"));
        buyUser.setDollars(new BigDecimal("10000"));
        buyUser = service.addUser(buyUser);

        User sellUser = new User();
        sellUser.setUsername("testSeller");
        sellUser.setPassword("testSeller");
        sellUser.setCoin(new BigDecimal("200"));
        sellUser.setDollars(new BigDecimal("9990"));
        sellUser = service.addUser(sellUser);

        Order buyOrder = new Order();
        buyOrder.setDateTime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder.setAmount(new BigDecimal("3.202"));
        buyOrder.setPrice(new BigDecimal("400.23232"));
        buyOrder.setIsComplete(false);
        buyOrder.setIsSell(false);
        buyOrder.setUser(buyUser);
        buyOrder = service.createOrder(buyOrder);

        Order sellOrder = new Order();
        sellOrder.setDateTime(LocalDateTime.parse("15-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder.setAmount(new BigDecimal("3.202"));
        sellOrder.setPrice(new BigDecimal("400.23232"));
        sellOrder.setIsComplete(false);
        sellOrder.setIsSell(true);
        sellOrder.setUser(sellUser);
        sellOrder = service.createOrder(sellOrder);

        Trade trade = new Trade();
        trade.setDatetime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade.setAmount(new BigDecimal("3.202"));
        trade.setPrice(new BigDecimal("400.23232"));
        trade.setBuyOrder(buyOrder);
        trade.setSellOrder(sellOrder);

        Trade addedTrade = service.createTrade(buyOrder, sellOrder);

        trade.setDatetime(addedTrade.getDatetime());
        trade.setId(addedTrade.getId());

        assertEquals(trade, addedTrade);

        Trade retrievedTrade = service.getTradeById(trade.getId());

        assertEquals(retrievedTrade, addedTrade);

        assertTrue(service.getOrderById(buyOrder.getId()).getIsComplete());
        assertTrue(service.getOrderById(sellOrder.getId()).getIsComplete());
    }

    @Test
    public void testCreateTradeHigherBuyAmount() {
        User buyUser = new User();
        buyUser.setUsername("testBuyer");
        buyUser.setPassword("testBuyer");
        buyUser.setCoin(new BigDecimal("10"));
        buyUser.setDollars(new BigDecimal("10000"));
        buyUser = service.addUser(buyUser);

        User sellUser = new User();
        sellUser.setUsername("testSeller");
        sellUser.setPassword("testSeller");
        sellUser.setCoin(new BigDecimal("200"));
        sellUser.setDollars(new BigDecimal("9990"));
        sellUser = service.addUser(sellUser);

        Order buyOrder = new Order();
        buyOrder.setDateTime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder.setAmount(new BigDecimal("50"));
        buyOrder.setPrice(new BigDecimal("400.23232"));
        buyOrder.setIsComplete(false);
        buyOrder.setIsSell(false);
        buyOrder.setUser(buyUser);
        buyOrder = service.createOrder(buyOrder);

        Order sellOrder = new Order();
        sellOrder.setDateTime(LocalDateTime.parse("15-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder.setAmount(new BigDecimal("40"));
        sellOrder.setPrice(new BigDecimal("400.23232"));
        sellOrder.setIsComplete(false);
        sellOrder.setIsSell(true);
        sellOrder.setUser(sellUser);
        sellOrder = service.createOrder(sellOrder);

        Trade trade = new Trade();
        trade.setDatetime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade.setAmount(new BigDecimal("40"));
        trade.setPrice(new BigDecimal("400.23232"));
        trade.setBuyOrder(buyOrder);
        trade.setSellOrder(sellOrder);

        Trade addedTrade = service.createTrade(buyOrder, sellOrder);

        trade.setDatetime(addedTrade.getDatetime());
        trade.setId(addedTrade.getId());

        assertEquals(trade, addedTrade);

        Trade retrievedTrade = service.getTradeById(trade.getId());

        assertEquals(retrievedTrade, addedTrade);

        assertTrue(service.getOrderById(buyOrder.getId()).getIsComplete());
        assertEquals(new BigDecimal("40").setScale(5, RoundingMode.HALF_UP), service.getOrderById(buyOrder.getId()).getAmount());
        assertTrue(service.getOrderById(sellOrder.getId()).getIsComplete());

        Order newBuyOrder = new Order();
        newBuyOrder.setDateTime(buyOrder.getDateTime());
        newBuyOrder.setAmount(new BigDecimal("10"));
        newBuyOrder.setPrice(new BigDecimal("400.23232"));
        newBuyOrder.setIsComplete(false);
        newBuyOrder.setIsSell(false);
        newBuyOrder.setUser(buyUser);

        Order retrievedNewBuyOrder = service.getOrderById(sellOrder.getId() + 1);
        newBuyOrder.setId(retrievedNewBuyOrder.getId());
        assertEquals(retrievedNewBuyOrder, newBuyOrder);
    }

    @Test
    public void testCreateTradeHigherSellAmount() {
        User buyUser = new User();
        buyUser.setUsername("testBuyer");
        buyUser.setPassword("testBuyer");
        buyUser.setCoin(new BigDecimal("10"));
        buyUser.setDollars(new BigDecimal("10000"));
        buyUser = service.addUser(buyUser);

        User sellUser = new User();
        sellUser.setUsername("testSeller");
        sellUser.setPassword("testSeller");
        sellUser.setCoin(new BigDecimal("200"));
        sellUser.setDollars(new BigDecimal("9990"));
        sellUser = service.addUser(sellUser);

        Order buyOrder = new Order();
        buyOrder.setDateTime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder.setAmount(new BigDecimal("70"));
        buyOrder.setPrice(new BigDecimal("400.23232"));
        buyOrder.setIsComplete(false);
        buyOrder.setIsSell(false);
        buyOrder.setUser(buyUser);
        buyOrder = service.createOrder(buyOrder);

        Order sellOrder = new Order();
        sellOrder.setDateTime(LocalDateTime.parse("15-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder.setAmount(new BigDecimal("90"));
        sellOrder.setPrice(new BigDecimal("400.23232"));
        sellOrder.setIsComplete(false);
        sellOrder.setIsSell(true);
        sellOrder.setUser(sellUser);
        sellOrder = service.createOrder(sellOrder);

        Trade trade = new Trade();
        trade.setDatetime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        trade.setAmount(new BigDecimal("70"));
        trade.setPrice(new BigDecimal("400.23232"));
        trade.setBuyOrder(buyOrder);
        trade.setSellOrder(sellOrder);

        Trade addedTrade = service.createTrade(buyOrder, sellOrder);

        trade.setDatetime(addedTrade.getDatetime());
        trade.setId(addedTrade.getId());

        assertEquals(trade, addedTrade);

        Trade retrievedTrade = service.getTradeById(trade.getId());

        assertEquals(retrievedTrade, addedTrade);

        assertTrue(service.getOrderById(buyOrder.getId()).getIsComplete());
        assertEquals(new BigDecimal("70").setScale(5, RoundingMode.HALF_UP), service.getOrderById(buyOrder.getId()).getAmount());
        assertTrue(service.getOrderById(sellOrder.getId()).getIsComplete());

        Order newSellOrder = new Order();
        newSellOrder.setDateTime(sellOrder.getDateTime());
        newSellOrder.setAmount(new BigDecimal("20"));
        newSellOrder.setPrice(new BigDecimal("400.23232"));
        newSellOrder.setIsComplete(false);
        newSellOrder.setIsSell(false);
        newSellOrder.setUser(sellUser);

        Order retrievedNewSellOrder = service.getOrderById(sellOrder.getId() + 1);
        newSellOrder.setId(retrievedNewSellOrder.getId());

        assertEquals(retrievedNewSellOrder, newSellOrder);
    }

    @Test
    public void testGetBuyOrders() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = service.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = service.createOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = service.createOrder(order3);

        List<Order> orders = service.getBuyOrders();
        assertEquals(1, orders.size());
        assertFalse(orders.contains(order2));
        assertFalse(orders.contains(order3));
        assertTrue(orders.contains(order));
    }

    @Test
    public void testGetSellOrders() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = service.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = service.createOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = service.createOrder(order3);

        List<Order> orders = service.getSellOrders();
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order2));
        assertTrue(orders.contains(order3));
        assertFalse(orders.contains(order));
    }

    @Test
    public void testGetBuyOrdersForUser() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = service.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = service.createOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = service.createOrder(order3);

        List<Order> orders = service.getBuyOrdersForUser(user);
        assertEquals(1, orders.size());
        assertFalse(orders.contains(order2));
        assertFalse(orders.contains(order3));
        assertTrue(orders.contains(order));
    }

    @Test
    public void testGetSellOrdersForUser() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = service.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = service.createOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = service.createOrder(order3);

        List<Order> orders = service.getSellOrdersForUser(user);
        assertEquals(1, orders.size());
        assertTrue(orders.contains(order2));
        assertFalse(orders.contains(order3));
        assertFalse(orders.contains(order));
    }

    @Test
    public void testGetAllOrders() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = service.createOrder(order2);

        List<Order> orders = service.getAllOrders();
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order));
        assertTrue(orders.contains(order2));
    }

    @Test
    public void testGetOrdersByUser() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = service.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = service.createOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = service.createOrder(order3);

        List<Order> orders = service.getOrdersByUser(user);
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
        user = service.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = service.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = service.createOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = service.createOrder(order3);

        List<Order> orders = service.getOrdersByDate(
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
        user = service.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = service.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(false);
        order2 = service.createOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(false);
        order3 = service.createOrder(order3);

        List<Order> orders = service.getOrdersByDate(
                LocalDateTime.of(2020, Month.SEPTEMBER, 16, 0, 0, 0),
                LocalDateTime.of(2020, Month.SEPTEMBER, 17, 23, 59, 59),
                user);

        assertEquals(1, orders.size());
        assertTrue(orders.contains(order2));
        assertFalse(orders.contains(order));
        assertFalse(orders.contains(order3));
    }

    @Test
    public void testGetOrdersByCompletion() {
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);
        user = service.addUser(user);

        User user2 = new User();
        user2.setUsername("u");
        user2.setPassword("u");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);
        user2 = service.addUser(user2);

        Order order = new Order();
        order.setUser(user);
        order.setIsSell(false);
        order.setPrice(BigDecimal.ONE);
        order.setAmount(BigDecimal.ONE);
        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
        order.setIsComplete(false);
        order = service.createOrder(order);

        Order order2 = new Order();
        order2.setUser(user);
        order2.setIsSell(true);
        order2.setPrice(new BigDecimal("2"));
        order2.setAmount(new BigDecimal("2"));
        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
        order2.setIsComplete(true);
        order2 = service.createOrder(order2);

        Order order3 = new Order();
        order3.setUser(user2);
        order3.setIsSell(true);
        order3.setPrice(new BigDecimal("3"));
        order3.setAmount(new BigDecimal("3"));
        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
        order3.setIsComplete(true);
        order3 = service.createOrder(order3);

        List<Order> orders = service.getOrdersByCompletion(true, user);
        assertEquals(1, orders.size());
        assertTrue(orders.contains(order2));
        assertFalse(orders.contains(order));
        assertFalse(orders.contains(order3));
    }

    @Test
    public void testGetAllTradesAndGetAllTradesForUser() {
        List<Trade> addedTrades = new ArrayList<>();

        User user1 = new User();
        user1.setUsername("testBuyer");
        user1.setPassword("testBuyer");
        user1.setCoin(new BigDecimal("10"));
        user1.setDollars(new BigDecimal("10000"));
        user1 = service.addUser(user1);

        User user2 = new User();
        user2.setUsername("testSeller");
        user2.setPassword("testSeller");
        user2.setCoin(new BigDecimal("200"));
        user2.setDollars(new BigDecimal("9990"));
        user2 = service.addUser(user2);

        User user3 = new User();
        user3.setUsername("testDude");
        user3.setPassword("testDude");
        user3.setCoin(new BigDecimal("200"));
        user3.setDollars(new BigDecimal("9990"));
        user3 = service.addUser(user3);

        Order buyOrder1 = new Order();
        buyOrder1.setDateTime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder1.setAmount(new BigDecimal("3.202"));
        buyOrder1.setPrice(new BigDecimal("400.23232"));
        buyOrder1.setIsComplete(true);
        buyOrder1.setIsSell(false);
        buyOrder1.setUser(user1);
        buyOrder1 = service.createOrder(buyOrder1);

        Order sellOrder1 = new Order();
        sellOrder1.setDateTime(LocalDateTime.parse("15-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder1.setAmount(new BigDecimal("3.202"));
        sellOrder1.setPrice(new BigDecimal("400.23232"));
        sellOrder1.setIsComplete(true);
        sellOrder1.setIsSell(true);
        sellOrder1.setUser(user2);
        sellOrder1 = service.createOrder(sellOrder1);

        Trade trade1 = new Trade();
        trade1.setAmount(new BigDecimal("3.202"));
        trade1.setPrice(new BigDecimal("400.23232"));
        trade1.setBuyOrder(buyOrder1);
        trade1.setSellOrder(sellOrder1);

        Trade addedTrade1 = service.createTrade(buyOrder1, sellOrder1);

        trade1.setDatetime(addedTrade1.getDatetime());
        trade1.setId(addedTrade1.getId());

        addedTrades.add(addedTrade1);

        Order buyOrder2 = new Order();
        buyOrder2.setDateTime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder2.setAmount(new BigDecimal("67.02"));
        buyOrder2.setPrice(new BigDecimal("500.23232"));
        buyOrder2.setIsComplete(true);
        buyOrder2.setIsSell(false);
        buyOrder2.setUser(user1);
        buyOrder2 = service.createOrder(buyOrder2);

        Order sellOrder2 = new Order();
        sellOrder2.setDateTime(LocalDateTime.parse("05-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder2.setAmount(new BigDecimal("67.02"));
        sellOrder2.setPrice(new BigDecimal("500.23232"));
        sellOrder2.setIsComplete(true);
        sellOrder2.setIsSell(true);
        sellOrder2.setUser(user3);
        sellOrder2 = service.createOrder(sellOrder2);

        Trade trade2 = new Trade();
        trade2.setAmount(new BigDecimal("67.02"));
        trade2.setPrice(new BigDecimal("500.23232"));
        trade2.setBuyOrder(buyOrder2);
        trade2.setSellOrder(sellOrder2);

        Trade addedTrade2 = service.createTrade(buyOrder2, sellOrder2);

        trade2.setDatetime(addedTrade2.getDatetime());
        trade2.setId(addedTrade2.getId());

        addedTrades.add(addedTrade2);

        List<Trade> allRetrievedTrades = service.getAllTrades();

        assertEquals(2, allRetrievedTrades.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        assertTrue(allRetrievedTrades.contains(addedTrade2));

        List<Trade> allRetrievedTradesForUser1 = service.getAllTradesForUser(user1);
        assertEquals(2, allRetrievedTradesForUser1.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        assertTrue(allRetrievedTrades.contains(addedTrade2));

        List<Trade> allRetrievedTradesForUser2 = service.getAllTradesForUser(user2);
        assertEquals(1, allRetrievedTradesForUser2.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));

        List<Trade> allRetrievedTradesForUser3 = service.getAllTradesForUser(user3);
        assertEquals(1, allRetrievedTradesForUser3.size());
        assertTrue(allRetrievedTrades.contains(addedTrade2));

    }

    @Test
    public void testDeleteTradeById() {

    }

    @Test
    public void testGetTradesByDateRangeAndUser() {

        List<Trade> addedTrades = new ArrayList<>();

        User user1 = new User();
        user1.setUsername("testBuyer");
        user1.setPassword("testBuyer");
        user1.setCoin(new BigDecimal("10"));
        user1.setDollars(new BigDecimal("10000"));
        user1 = service.addUser(user1);

        User user2 = new User();
        user2.setUsername("testSeller");
        user2.setPassword("testSeller");
        user2.setCoin(new BigDecimal("200"));
        user2.setDollars(new BigDecimal("9990"));
        user2 = service.addUser(user2);

        User user3 = new User();
        user3.setUsername("testDude");
        user3.setPassword("testDude");
        user3.setCoin(new BigDecimal("200"));
        user3.setDollars(new BigDecimal("9990"));
        user3 = service.addUser(user3);

        Order buyOrder1 = new Order();
        buyOrder1.setDateTime(LocalDateTime.parse("10-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder1.setAmount(new BigDecimal("3.202"));
        buyOrder1.setPrice(new BigDecimal("400.23232"));
        buyOrder1.setIsComplete(true);
        buyOrder1.setIsSell(false);
        buyOrder1.setUser(user1);
        buyOrder1 = service.createOrder(buyOrder1);

        Order sellOrder1 = new Order();
        sellOrder1.setDateTime(LocalDateTime.parse("10-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder1.setAmount(new BigDecimal("3.202"));
        sellOrder1.setPrice(new BigDecimal("400.23232"));
        sellOrder1.setIsComplete(true);
        sellOrder1.setIsSell(true);
        sellOrder1.setUser(user2);
        sellOrder1 = service.createOrder(sellOrder1);

        Trade trade1 = new Trade();
        trade1.setAmount(new BigDecimal("3.202"));
        trade1.setPrice(new BigDecimal("400.23232"));
        trade1.setBuyOrder(buyOrder1);
        trade1.setSellOrder(sellOrder1);

        Trade addedTrade1 = service.createTrade(buyOrder1, sellOrder1);

        trade1.setDatetime(addedTrade1.getDatetime());
        trade1.setId(addedTrade1.getId());

        Order buyOrder2 = new Order();
        buyOrder2.setDateTime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder2.setAmount(new BigDecimal("67.02"));
        buyOrder2.setPrice(new BigDecimal("500.23232"));
        buyOrder2.setIsComplete(true);
        buyOrder2.setIsSell(false);
        buyOrder2.setUser(user1);
        buyOrder2 = service.createOrder(buyOrder2);

        Order sellOrder2 = new Order();
        sellOrder2.setDateTime(LocalDateTime.parse("05-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder2.setAmount(new BigDecimal("67.02"));
        sellOrder2.setPrice(new BigDecimal("500.23232"));
        sellOrder2.setIsComplete(true);
        sellOrder2.setIsSell(true);
        sellOrder2.setUser(user3);
        sellOrder2 = service.createOrder(sellOrder2);

        Trade trade2 = new Trade();
        trade2.setAmount(new BigDecimal("67.02"));
        trade2.setPrice(new BigDecimal("500.23232"));
        trade2.setBuyOrder(buyOrder2);
        trade2.setSellOrder(sellOrder2);

        Trade addedTrade2 = service.createTrade(buyOrder2, sellOrder2);

        trade2.setDatetime(addedTrade2.getDatetime());
        trade2.setId(addedTrade2.getId());

        List<Trade> allRetrievedTrades = tradeDao.getTradesByDateRange(LocalDateTime.of(2020, 9, 4, 12, 0, 0), LocalDateTime.of(2020, 9, 11, 12, 0, 0));

        assertEquals(2, allRetrievedTrades.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        assertTrue(allRetrievedTrades.contains(addedTrade2));

        List<Trade> RetrievedTradesForRange1 = tradeDao.getTradesByDateRange(LocalDateTime.of(2020, 9, 9, 12, 0, 0), LocalDateTime.of(2020, 9, 11, 12, 0, 0));
        assertEquals(1, RetrievedTradesForRange1.size());
        assertTrue(RetrievedTradesForRange1.contains(addedTrade1));

        List<Trade> RetrievedTradesForRange2 = tradeDao.getTradesByDateRange(LocalDateTime.of(2020, 9, 4, 12, 0, 0), LocalDateTime.of(2020, 9, 6, 12, 0, 0));
        assertEquals(1, RetrievedTradesForRange2.size());
        assertTrue(RetrievedTradesForRange2.contains(addedTrade2));

        List<Trade> RetrievedTradesForRange0User1 = tradeDao.getTradesByDateRangeAndUser(LocalDateTime.of(2020, 9, 4, 12, 0, 0), LocalDateTime.of(2020, 9, 11, 12, 0, 0), user1);
        assertEquals(2, RetrievedTradesForRange0User1.size());
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade1));
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade2));

        List<Trade> RetrievedTradesForRange1User1 = tradeDao.getTradesByDateRangeAndUser(LocalDateTime.of(2020, 9, 9, 12, 0, 0), LocalDateTime.of(2020, 9, 11, 12, 0, 0), user1);
        assertEquals(1, RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));

        List<Trade> RetrievedTradesForRange1User2 = tradeDao.getTradesByDateRangeAndUser(LocalDateTime.of(2020, 9, 9, 12, 0, 0), LocalDateTime.of(2020, 9, 11, 12, 0, 0), user2);
        assertEquals(1, RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));
    }

    @Test
    public void testGetTradesByPriceRangeAndUser() {
        List<Trade> addedTrades = new ArrayList<>();

        User user1 = new User();
        user1.setUsername("testBuyer");
        user1.setPassword("testBuyer");
        user1.setCoin(new BigDecimal("10"));
        user1.setDollars(new BigDecimal("10000"));
        user1 = service.addUser(user1);

        User user2 = new User();
        user2.setUsername("testSeller");
        user2.setPassword("testSeller");
        user2.setCoin(new BigDecimal("200"));
        user2.setDollars(new BigDecimal("9990"));
        user2 = service.addUser(user2);

        User user3 = new User();
        user3.setUsername("testDude");
        user3.setPassword("testDude");
        user3.setCoin(new BigDecimal("200"));
        user3.setDollars(new BigDecimal("9990"));
        user3 = service.addUser(user3);

        Order buyOrder1 = new Order();
        buyOrder1.setDateTime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder1.setAmount(new BigDecimal("3.202"));
        buyOrder1.setPrice(new BigDecimal("400.23232"));
        buyOrder1.setIsComplete(true);
        buyOrder1.setIsSell(false);
        buyOrder1.setUser(user1);
        buyOrder1 = service.createOrder(buyOrder1);

        Order sellOrder1 = new Order();
        sellOrder1.setDateTime(LocalDateTime.parse("15-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder1.setAmount(new BigDecimal("3.202"));
        sellOrder1.setPrice(new BigDecimal("400.23232"));
        sellOrder1.setIsComplete(true);
        sellOrder1.setIsSell(true);
        sellOrder1.setUser(user2);
        sellOrder1 = service.createOrder(sellOrder1);

        Trade trade1 = new Trade();
        trade1.setAmount(new BigDecimal("3.202"));
        trade1.setPrice(new BigDecimal("400.23232"));
        trade1.setBuyOrder(buyOrder1);
        trade1.setSellOrder(sellOrder1);

        Trade addedTrade1 = service.createTrade(buyOrder1, sellOrder1);

        trade1.setDatetime(addedTrade1.getDatetime());
        trade1.setId(addedTrade1.getId());

        Order buyOrder2 = new Order();
        buyOrder2.setDateTime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder2.setAmount(new BigDecimal("67.02"));
        buyOrder2.setPrice(new BigDecimal("500.23232"));
        buyOrder2.setIsComplete(true);
        buyOrder2.setIsSell(false);
        buyOrder2.setUser(user1);
        buyOrder2 = service.createOrder(buyOrder2);

        Order sellOrder2 = new Order();
        sellOrder2.setDateTime(LocalDateTime.parse("05-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder2.setAmount(new BigDecimal("67.02"));
        sellOrder2.setPrice(new BigDecimal("500.23232"));
        sellOrder2.setIsComplete(true);
        sellOrder2.setIsSell(true);
        sellOrder2.setUser(user3);
        sellOrder2 = service.createOrder(sellOrder2);

        Trade trade2 = new Trade();
        trade2.setAmount(new BigDecimal("67.02"));
        trade2.setPrice(new BigDecimal("500.23232"));
        trade2.setBuyOrder(buyOrder2);
        trade2.setSellOrder(sellOrder2);

        Trade addedTrade2 = service.createTrade(buyOrder2, sellOrder2);

        trade2.setDatetime(addedTrade2.getDatetime());
        trade2.setId(addedTrade2.getId());

        List<Trade> allRetrievedTrades = service.getTradesByPriceRange(new BigDecimal("350"), new BigDecimal("550"));
        assertEquals(2, allRetrievedTrades.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        assertTrue(allRetrievedTrades.contains(addedTrade2));

        List<Trade> RetrievedTradesForRange1 = service.getTradesByPriceRange(new BigDecimal("350"), new BigDecimal("450"));
        assertEquals(1, RetrievedTradesForRange1.size());
        assertTrue(RetrievedTradesForRange1.contains(addedTrade1));

        List<Trade> RetrievedTradesForRange2 = service.getTradesByPriceRange(new BigDecimal("450"), new BigDecimal("550"));
        assertEquals(1, RetrievedTradesForRange2.size());
        assertTrue(RetrievedTradesForRange2.contains(addedTrade2));

        List<Trade> RetrievedTradesForRange0User1 = service.getTradesByPriceRangeAndUser(new BigDecimal("350"), new BigDecimal("550"), user1);
        assertEquals(2, RetrievedTradesForRange0User1.size());
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade1));
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade2));

        List<Trade> RetrievedTradesForRange1User1 = service.getTradesByPriceRangeAndUser(new BigDecimal("350"), new BigDecimal("450"), user1);
        assertEquals(1, RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));

        List<Trade> RetrievedTradesForRange1User2 = service.getTradesByPriceRangeAndUser(new BigDecimal("350"), new BigDecimal("450"), user2);
        assertEquals(1, RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));

    }

    @Test
    public void testGetTradesByAmountRangeAndUser() {
        List<Trade> addedTrades = new ArrayList<>();

        User user1 = new User();
        user1.setUsername("testBuyer");
        user1.setPassword("testBuyer");
        user1.setCoin(new BigDecimal("10"));
        user1.setDollars(new BigDecimal("10000"));
        user1 = service.addUser(user1);

        User user2 = new User();
        user2.setUsername("testSeller");
        user2.setPassword("testSeller");
        user2.setCoin(new BigDecimal("200"));
        user2.setDollars(new BigDecimal("9990"));
        user2 = service.addUser(user2);

        User user3 = new User();
        user3.setUsername("testDude");
        user3.setPassword("testDude");
        user3.setCoin(new BigDecimal("200"));
        user3.setDollars(new BigDecimal("9990"));
        user3 = service.addUser(user3);

        Order buyOrder1 = new Order();
        buyOrder1.setDateTime(LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder1.setAmount(new BigDecimal("3.202"));
        buyOrder1.setPrice(new BigDecimal("400.23232"));
        buyOrder1.setIsComplete(true);
        buyOrder1.setIsSell(false);
        buyOrder1.setUser(user1);
        buyOrder1 = service.createOrder(buyOrder1);

        Order sellOrder1 = new Order();
        sellOrder1.setDateTime(LocalDateTime.parse("15-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder1.setAmount(new BigDecimal("3.202"));
        sellOrder1.setPrice(new BigDecimal("400.23232"));
        sellOrder1.setIsComplete(true);
        sellOrder1.setIsSell(true);
        sellOrder1.setUser(user2);
        sellOrder1 = service.createOrder(sellOrder1);

        Trade trade1 = new Trade();
        trade1.setAmount(new BigDecimal("3.202"));
        trade1.setPrice(new BigDecimal("400.23232"));
        trade1.setBuyOrder(buyOrder1);
        trade1.setSellOrder(sellOrder1);

        Trade addedTrade1 = service.createTrade(buyOrder1, sellOrder1);

        trade1.setDatetime(addedTrade1.getDatetime());
        trade1.setId(addedTrade1.getId());

        Order buyOrder2 = new Order();
        buyOrder2.setDateTime(LocalDateTime.parse("05-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        buyOrder2.setAmount(new BigDecimal("67.02"));
        buyOrder2.setPrice(new BigDecimal("500.23232"));
        buyOrder2.setIsComplete(true);
        buyOrder2.setIsSell(false);
        buyOrder2.setUser(user1);
        buyOrder2 = service.createOrder(buyOrder2);

        Order sellOrder2 = new Order();
        sellOrder2.setDateTime(LocalDateTime.parse("05-09-2020 10:10:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        sellOrder2.setAmount(new BigDecimal("67.02"));
        sellOrder2.setPrice(new BigDecimal("500.23232"));
        sellOrder2.setIsComplete(true);
        sellOrder2.setIsSell(true);
        sellOrder2.setUser(user3);
        sellOrder2 = service.createOrder(sellOrder2);

        Trade trade2 = new Trade();
        trade2.setAmount(new BigDecimal("67.02"));
        trade2.setPrice(new BigDecimal("500.23232"));
        trade2.setBuyOrder(buyOrder2);
        trade2.setSellOrder(sellOrder2);

        Trade addedTrade2 = service.createTrade(buyOrder2, sellOrder2);

        trade2.setDatetime(addedTrade2.getDatetime());
        trade2.setId(addedTrade2.getId());

        List<Trade> allRetrievedTrades = service.getTradesByAmountRange(new BigDecimal("2"), new BigDecimal("100"));
        assertEquals(2, allRetrievedTrades.size());
        assertTrue(allRetrievedTrades.contains(addedTrade1));
        assertTrue(allRetrievedTrades.contains(addedTrade2));

        List<Trade> RetrievedTradesForRange1 = service.getTradesByAmountRange(new BigDecimal("2"), new BigDecimal("10"));
        assertEquals(1, RetrievedTradesForRange1.size());
        assertTrue(RetrievedTradesForRange1.contains(addedTrade1));

        List<Trade> RetrievedTradesForRange2 = service.getTradesByAmountRange(new BigDecimal("10"), new BigDecimal("100"));
        assertEquals(1, RetrievedTradesForRange2.size());
        assertTrue(RetrievedTradesForRange2.contains(addedTrade2));

        List<Trade> RetrievedTradesForRange0User1 = service.getTradesByAmountRangeAndUser(new BigDecimal("2"), new BigDecimal("100"), user1);
        assertEquals(2, RetrievedTradesForRange0User1.size());
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade1));
        assertTrue(RetrievedTradesForRange0User1.contains(addedTrade2));

        List<Trade> RetrievedTradesForRange1User1 = service.getTradesByAmountRangeAndUser(new BigDecimal("2"), new BigDecimal("10"), user1);
        assertEquals(1, RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));

        List<Trade> RetrievedTradesForRange1User2 = service.getTradesByAmountRangeAndUser(new BigDecimal("2"), new BigDecimal("10"), user2);
        assertEquals(1, RetrievedTradesForRange1User1.size());
        assertTrue(RetrievedTradesForRange1User1.contains(addedTrade1));
    }

    @Test
    public void testAndAndGetOpeningPrice() {
        BigDecimal tickSize = new BigDecimal("0.05");
        BigDecimal percentage = new BigDecimal("0.01");
        BigDecimal high = new BigDecimal("11000.00");
        BigDecimal low = new BigDecimal("9000.00");
        BigDecimal price = new BigDecimal("10000.03");

        Share share = new Share();
        share.setName("test");
        share.setSymbol("test");
        share.setTickSize(tickSize);
        share.setPercentage(percentage);
        share.setHigh(high);
        share.setLow(low);
        share.setPrice(price);

        share = service.addShare(share);

        OpeningPrices op = new OpeningPrices();

        LocalDateTime myDateObj = LocalDateTime.now();
        myDateObj = LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        op.setDatetime(myDateObj);
        op.setPrice(price);
        op.setShareId(share.getId());

        op = service.addOpeningPrices(op);

        OpeningPrices fromDao = service.getOpeningPrice(myDateObj, share.getId());

        assertEquals(op, fromDao);
    }

    @Test
    public void testGetAllOpeningPrices() {
        BigDecimal tickSize = new BigDecimal("0.05");
        BigDecimal percentage = new BigDecimal("0.01");
        BigDecimal high = new BigDecimal("11000.00");
        BigDecimal low = new BigDecimal("9000.00");
        BigDecimal price = new BigDecimal("10000.03");

        Share share = new Share();
        share.setName("test");
        share.setSymbol("test");
        share.setTickSize(tickSize);
        share.setPercentage(percentage);
        share.setHigh(high);
        share.setLow(low);
        share.setPrice(price);

        share = service.addShare(share);

        OpeningPrices op = new OpeningPrices();

        LocalDateTime myDateObj = LocalDateTime.now();
        myDateObj = LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        op.setDatetime(myDateObj);
        op.setPrice(price);
        op.setShareId(share.getId());

        op = service.addOpeningPrices(op);

        OpeningPrices op2 = new OpeningPrices();

        LocalDateTime myDateObj2 = LocalDateTime.now();
        myDateObj2 = LocalDateTime.parse("15-09-2020 12:12:12", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        op2.setDatetime(myDateObj2);
        op2.setPrice(price);
        op2.setShareId(share.getId());

        op2 = service.addOpeningPrices(op2);

        List<OpeningPrices> ops = service.getAllOpeningPrices();

        assertEquals(2, ops.size());
        assertTrue(ops.contains(op));
        assertTrue(ops.contains(op2));
    }

    @Test
    public void testDeleteOpeningPrices() {
        BigDecimal tickSize = new BigDecimal("0.05");
        BigDecimal percentage = new BigDecimal("0.01");
        BigDecimal high = new BigDecimal("11000.00");
        BigDecimal low = new BigDecimal("9000.00");
        BigDecimal price = new BigDecimal("10000.03");

        Share share = new Share();
        share.setName("test");
        share.setSymbol("test");
        share.setTickSize(tickSize);
        share.setPercentage(percentage);
        share.setHigh(high);
        share.setLow(low);
        share.setPrice(price);

        share = service.addShare(share);

        OpeningPrices op = new OpeningPrices();

        LocalDateTime myDateObj = LocalDateTime.now();
        myDateObj = LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        op.setDatetime(myDateObj);
        op.setPrice(price);
        op.setShareId(share.getId());

        op = service.addOpeningPrices(op);

        OpeningPrices fromDao = service.getOpeningPrice(myDateObj, share.getId());
        assertEquals(op, fromDao);

        service.deleteOpeningPrices(myDateObj, op.getShareId());
        fromDao = service.getOpeningPrice(myDateObj, share.getId());

        assertNull(fromDao);

    }

    @Test
    public void testGetAllShares() {
        BigDecimal tickSize = new BigDecimal("0.05");
        BigDecimal percentage = new BigDecimal("0.01");
        BigDecimal high = new BigDecimal("11000.00");
        BigDecimal low = new BigDecimal("9000.00");
        BigDecimal price = new BigDecimal("10000.03");

        Share share = new Share();
        share.setName("test");
        share.setSymbol("test");
        share.setTickSize(tickSize);
        share.setPercentage(percentage);
        share.setHigh(high);
        share.setLow(low);
        share.setPrice(price);

        share = service.addShare(share);

        Share share2 = new Share();
        share2.setName("test2");
        share2.setSymbol("test2");
        share2.setTickSize(tickSize);
        share2.setPercentage(percentage);
        share2.setHigh(high);
        share2.setLow(low);
        share2.setPrice(price);

        share2 = service.addShare(share2);

        List<Share> shares = service.getAllShares();

        assertEquals(2, shares.size());
        assertTrue(shares.contains(share));
        assertTrue(shares.contains(share2));
    }

    @Test
    public void testAddShareAndGetShareById() {
        BigDecimal tickSize = new BigDecimal("0.05");
        BigDecimal percentage = new BigDecimal("0.01");
        BigDecimal high = new BigDecimal("11000.00");
        BigDecimal low = new BigDecimal("9000.00");
        BigDecimal price = new BigDecimal("10000.03");

        Share share = new Share();
        share.setName("test");
        share.setSymbol("test");
        share.setTickSize(tickSize);
        share.setPercentage(percentage);
        share.setHigh(high);
        share.setLow(low);
        share.setPrice(price);

        share = service.addShare(share);
        Share fromShareDao = service.getShareById(share.getId());

        assertEquals(share, fromShareDao);
    }

    @Test
    public void testUpdateShare() {
        BigDecimal tickSize = new BigDecimal("0.05");
        BigDecimal percentage = new BigDecimal("0.01");
        BigDecimal high = new BigDecimal("11000.00");
        BigDecimal low = new BigDecimal("9000.00");
        BigDecimal price = new BigDecimal("10000.03");

        Share share = new Share();
        share.setName("test");
        share.setSymbol("test");
        share.setTickSize(tickSize);
        share.setPercentage(percentage);
        share.setHigh(high);
        share.setLow(low);
        share.setPrice(price);

        share = service.addShare(share);

        Share fromShareDao = service.getShareById(share.getId());

        assertEquals(share, fromShareDao);

        share.setName("testChange");

        service.updateShare(share);

        Share updatedShare = service.getShareById(share.getId());

        assertNotEquals(updatedShare, fromShareDao);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setUsername("alibaba");
        user1.setPassword("yaay");
        user1.setCoin(BigDecimal.ONE);
        user1.setDollars(BigDecimal.ONE);

        user1 = service.addUser(user1);

        User user2 = new User();
        user2.setUsername("alibaba2");
        user2.setPassword("yayay");
        user2.setCoin(BigDecimal.ONE);
        user2.setDollars(BigDecimal.ONE);

        user2 = service.addUser(user2);

        List<User> retrievedUsers = service.getAllUsers();
        assertEquals(2, retrievedUsers.size());
        assertTrue(retrievedUsers.contains(user1));
        assertTrue(retrievedUsers.contains(user2));
    }

    @Test
    public void testAddUserAndGetUserById() {
        User user = new User();
        user.setUsername("alibaba");
        user.setPassword("yaay");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);

        user = service.addUser(user);

        User fromDao = service.getUserById(user.getId());

        assertEquals(user, fromDao);

    }

    @Test
    public void testDeleteUserById() {
        User user = new User();
        user.setUsername("alibaba");
        user.setPassword("yaay");
        user.setCoin(BigDecimal.ONE);
        user.setDollars(BigDecimal.ONE);

        user = service.addUser(user);
    }

}
