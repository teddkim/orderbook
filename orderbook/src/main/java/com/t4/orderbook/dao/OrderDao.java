/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.User;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author kennethan
 */
public interface OrderDao {

    /**
     * Retrieve an order by its id. If the order doesn't exist, return null.
     *
     * @param id of the order
     * @return Order of that id, or null if id does not associate with any order
     */
    Order getOrderById(int id);

    /**
     * Retrieve all orders that exist in the database.
     *
     * @return List of all Orders
     */
    List<Order> getAllOrders();

    /**
     * Add a new order to the database.
     *
     * @param order that is being added to the database
     * @return the Order added
     */
    Order addOrder(Order order);

    /**
     * Update an existing order in the database.Nothing happens if the order
     * does not exist.
     *
     * @param order to be updated in the database
     * @return true if update is successful, false if not
     */
    boolean updateOrder(Order order);

    /**
     * Delete an order associated with a given id.Delete is not performed if an
     * order with that id does not exist.
     *
     * @param id of the order to be deleted
     * @return true if deletion is successful, false if not
     */
    boolean deleteOrderById(int id);

    /**
     * Retrieve a list of Orders of a specific user.
     *
     * @param user of the orders
     * @return list of orders of that user
     */
    List<Order> getOrdersByUser(User user);

    /**
     * Retrieve a list of Orders based on a range of dates.
     *
     * @param startDate the starting date of the range of orders
     * @param endDate the ending date of the range of orders
     * @return list of orders in the range of dates
     */
    List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Retrieve a list of Orders based on a range of dates and user.
     *
     * @param startDate the starting date of the range of orders
     * @param endDate the ending date of the range of orders
     * @param user of the orders
     * @return list of orders in the range of dates and user
     */
    List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate, User user);

    /**
     * Retrieve a list of Orders based on its type: buy or sell.
     *
     * @param isSell boolean that indicates whether the order is a buy or sell
     * @return list of orders of that type
     */
    List<Order> getOrdersByType(boolean isSell);

    /**
     * Retrieve a list of Orders based on its type (buy and sell) and user.
     *
     * @param isSell boolean that indicates whether the order is a buy or sell
     * @param user of the orders
     * @return list of orders of that type and user
     */
    List<Order> getOrdersByType(boolean isSell, User user);

    /**
     * Retrieve a list of Orders based on its completion.
     *
     * @param isComplete boolean that indicates whether the order has been
     * performed
     * @return list of orders of its completion status
     */
    List<Order> getOrdersByCompletion(boolean isComplete);

    /**
     * Retrieve a list of Orders based on its completion and user.
     *
     * @param isComplete boolean that indicates whether the order has been
     * performed
     * @param user of the orders
     * @return list of orders of its completion status and user
     */
    List<Order> getOrdersByCompletion(boolean isComplete, User user);

}
