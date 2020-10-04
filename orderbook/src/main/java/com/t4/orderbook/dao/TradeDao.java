/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.Trade;
import com.t4.orderbook.entities.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author AmanK
 */
public interface TradeDao {

    /**
     * Retrieves a trade by its id. If the trade does not exist, return null.
     *
     * @param id of the trade
     * @return Trade with that id, or null if a trade with that id does not
     * exist.
     */
    Trade getTradeById(int id);

    /**
     * Retrieves all trades. If no trades exist, return null.
     *
     * @return List of trades
     */
    List<Trade> getAllTrades();

    /**
     * Retrieves all trades for a particular user. If no trades exist, return
     * null.
     *
     * @param user
     * @return List of trades for the particular user
     */
    List<Trade> getAllTradesForUser(User user);

    /**
     * Delete the trade with that id.
     *
     * @param id of the trade to be deleted.
     */
    void deleteTradeById(int id);

    /**
     * Updates the fields of the trade.
     *
     * @param Trade object with updated fields
     */
    void updateTrade(Trade trade);

    /**
     * Adds a trade to the Data Layer.
     *
     * @param Trade object to be added
     * @return Trade object after being added
     */
    Trade addTrade(Trade trade);

    /**
     * Retrieves all trades in the range of dates.
     *
     * @param startDatetime is the starting date and time of the range
     * @param endDatetime is the ending date and time of the range
     * @return List of trades in the given date range
     */
    List<Trade> getTradesByDateRange(LocalDateTime startDatetime, LocalDateTime endDatetime);

    /**
     * Retrieves all trades in the range of dates for the particular user.
     *
     * @param startDatetime is the starting date and time of the range
     * @param endDatetime is the ending date and time of the range
     * @param user is the user whose trades should be retrieved
     * @return List of trades in the given date range for the particular user
     */
    List<Trade> getTradesByDateRangeAndUser(LocalDateTime startDatetime, LocalDateTime endDatetime, User user);

    /**
     * Retrieves all trades in the range of prices.
     *
     * @param startPrice is the starting price of the range
     * @param endPrice is the ending price of the range
     * @return List of trades in the given price range
     */
    List<Trade> getTradesByPriceRange(BigDecimal startPrice, BigDecimal endPrice);

    /**
     * Retrieves all trades in the range of prices for the particular user.
     *
     * @param startPrice is the starting price of the range
     * @param endPrice is the ending price of the range
     * @param user is the user whose trades should be retrieved
     * @return List of trades in the given price range for the particular user
     */
    List<Trade> getTradesByPriceRangeAndUser(BigDecimal startPrice, BigDecimal endPrice, User user);

    /**
     * Retrieves all trades in the range of amounts.
     *
     * @param startAmount is the starting amount of the range
     * @param endAmount is the ending amount of the range
     * @return List of trades in the given amount range
     */
    List<Trade> getTradesByAmountRange(BigDecimal startAmount, BigDecimal endAmount);

    /**
     * Retrieves all trades in the range of amounts for the particular user.
     *
     * @param startAmount is the starting amount of the range
     * @param endAmount is the ending amount of the range
     * @param user is the user whose trades should be retrieved
     * @return List of trades in the given amount range for the particular user
     */
    List<Trade> getTradesByAmountRangeAndUser(BigDecimal startAmount, BigDecimal endAmount, User user);
    
    List<Trade> getLastTenTrades(User user);
}
