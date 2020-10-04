/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.service;

import com.t4.orderbook.entities.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author kennethan
 */
public interface OrderbookService {

    /**
     * Create a randomly generated buy order.
     *
     * @return a new buy order
     */
    Order createRandomBuyOrder();

    /**
     * Create a randomly generated sell order.
     *
     * @return a new sell order
     */
    Order createRandomSellOrder();

    /**
     *
     * @param order
     * @return
     */
    Order createOrder(Order order);

    /**
     * Can't update completed orders
     *
     * @param order
     * @return
     */
    Order updateOrder(Order order);

    /**
     * Can't delete completed orders
     *
     * @param order
     * @return
     */
    boolean deleteOrder(Order order);

    /**
     * Get the ask price, which is the minimum price of a sell.
     *
     * @return minimum price
     */
    Order getAsk();

    /**
     * Get the bid price, which is the maximum price of a buy.
     *
     * @return maximum price
     */
    Order getBid();

    /**
     * This function will update orders.
     *
     * @param buy
     * @param sell
     * @return
     */
    Trade createTrade(Order buy, Order sell);

    /**
     * Retrieve all buy orders
     *
     * @return
     */
    List<Order> getBuyOrders();

    /**
     * Retrieve all sell orders
     *
     * @return
     */
    List<Order> getSellOrders();

    List<Order> getBuyOrdersForUser(User user);

    List<Order> getSellOrdersForUser(User user);

    /**
     *
     * @param id
     * @return
     */
    Order getOrderById(int id);

    /**
     *
     * @return
     */
    List<Order> getAllOrders();

    /**
     *
     * @param user
     * @return
     */
    List<Order> getOrdersByUser(User user);

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate);

    /**
     *
     * @param startDate
     * @param endDate
     * @param user
     * @return
     */
    List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate, User user);

    /**
     *
     * @param isComplete
     * @return
     */
    List<Order> getOrdersByCompletion(boolean isComplete);

    /**
     *
     * @param isComplete
     * @param user
     * @return
     */
    List<Order> getOrdersByCompletion(boolean isComplete, User user);

    /**
     *
     * @param id
     * @return
     */
    Trade getTradeById(int id);

    /**
     *
     * @return
     */
    List<Trade> getAllTrades();

    /**
     *
     * @param user
     * @return
     */
    List<Trade> getAllTradesForUser(User user);

    /**
     *
     * @param id
     */
    void deleteTradeById(int id);

    /**
     *
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    List<Trade> getTradesByDateRange(LocalDateTime startDatetime, LocalDateTime endDatetime);

    /**
     *
     * @param startDatetime
     * @param endDatetime
     * @param user
     * @return
     */
    List<Trade> getTradesByDateRangeAndUser(LocalDateTime startDatetime, LocalDateTime endDatetime, User user);

    /**
     *
     * @param startPrice
     * @param endPrice
     * @return
     */
    List<Trade> getTradesByPriceRange(BigDecimal startPrice, BigDecimal endPrice);

    /**
     *
     * @param startPrice
     * @param endPrice
     * @param user
     * @return
     */
    List<Trade> getTradesByPriceRangeAndUser(BigDecimal startPrice, BigDecimal endPrice, User user);

    /**
     *
     * @param startAmount
     * @param endAmount
     * @return
     */
    List<Trade> getTradesByAmountRange(BigDecimal startAmount, BigDecimal endAmount);

    /**
     *
     * @param startAmount
     * @param endAmount
     * @param user
     * @return
     */
    List<Trade> getTradesByAmountRangeAndUser(BigDecimal startAmount, BigDecimal endAmount, User user);

    /**
     *
     * @param datetime
     * @param shareId
     * @return
     */
    OpeningPrices getOpeningPrice(LocalDateTime datetime, int shareId);

    /**
     *
     * @return
     */
    List<OpeningPrices> getAllOpeningPrices();

    /**
     *
     * @param openingPrices
     * @return
     */
    OpeningPrices addOpeningPrices(OpeningPrices openingPrices);

    /**
     *
     * @param datetime
     * @param shareId
     */
    void deleteOpeningPrices(LocalDateTime datetime, int shareId);

    /**
     *
     * @param id
     * @return
     */
    Share getShareById(int id);

    /**
     *
     * @return
     */
    List<Share> getAllShares();

    /**
     *
     * @param share
     * @return
     */
    Share addShare(Share share);

    /**
     *
     * @param share
     */
    Share updateShare(Share share);

    /**
     *
     * @return
     */
    List<User> getAllUsers();

    /**
     *
     * @param id
     * @return
     */
    User getUserById(int id);

    /**
     *
     * @param user
     * @return
     */
    User addUser(User user);

    /**
     *
     * @param id
     */
    boolean deleteUserById(int id);

    /**
     *
     * @param datetime
     * @param shareId
     * @param trade
     * @return
     */
    BigDecimal getPercentage(LocalDateTime datetime, int shareId, Trade trade);

    List<Order> getSortedSells();

    List<Order> getSortedBuys();

    List<Trade> getDataPoints();

    List<Trade> getDataPointsForWeek();

    List<Trade> getTenTrades(User user);
}
