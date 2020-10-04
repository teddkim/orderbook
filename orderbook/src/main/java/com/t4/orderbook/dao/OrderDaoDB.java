/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.dao.UserDaoDB.UserMapper;
import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kennethan
 */
@Repository
public class OrderDaoDB implements OrderDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Order getOrderById(int id) {
        try {
            final String GET_ORDER_BY_ID = "SELECT * FROM OrderTable WHERE id = ?";
            Order order = jdbc.queryForObject(GET_ORDER_BY_ID, new OrderMapper(), id);
            order.setUser(getUserForOrder(order));
            return order;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        final String GET_ORDERS_BY_USER = "SELECT * FROM OrderTable WHERE userId = ?";
        List<Order> orders = jdbc.query(GET_ORDERS_BY_USER, new OrderMapper(), user.getId());
        for (Order order : orders) {
            order.setUser(getUserForOrder(order));
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        final String GET_ALL_ORDERS = "SELECT * FROM OrderTable";
        List<Order> orders = jdbc.query(GET_ALL_ORDERS, new OrderMapper());
        for (Order order : orders) {
            order.setUser(getUserForOrder(order));
        }
        return orders;
    }

    @Override
    @Transactional
    public Order addOrder(Order order) {
        final String ADD_ORDER = "INSERT INTO OrderTable(type, price, amount, datetime, completed, userId) VALUES(?, ?, ?, ?, ?, ?)";
        int type = 0, completed = 0;
        if (order.getIsSell()) {
            type = 1;
        }
        if (order.getIsComplete()) {
            completed = 1;
        }
        jdbc.update(ADD_ORDER,
                type,
                order.getPrice(),
                order.getAmount(),
                order.getDateTime(),
                completed,
                order.getUser().getId()
        );
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        order.setId(newId);
        return order;
    }

    @Override
    @Transactional
    public boolean updateOrder(Order order) {
        final String UPDATE_ORDER = "UPDATE OrderTable SET type = ?, price = ?, amount = ?, datetime = ?, completed = ?, userId = ? WHERE id = ?";
        int type = order.getIsSell() ? 1 : 0;
        int completed = order.getIsComplete() ? 1 : 0;
        int success = jdbc.update(UPDATE_ORDER,
                type,
                order.getPrice(),
                order.getAmount(),
                Timestamp.valueOf(order.getDateTime()),
                completed,
                order.getUser().getId(),
                order.getId());
        return success > 0;
    }

    @Override
    @Transactional
    public boolean deleteOrderById(int id) {
        final String DELETE_ORDER = "DELETE FROM OrderTable WHERE id = ?";
        int x = jdbc.update(DELETE_ORDER, id);
        return x > 0;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate) {
        final String GET_ORDERS_BY_DATE = "SELECT * FROM OrderTable "
                + "WHERE datetime BETWEEN ? and ?";
        List<Order> orders = jdbc.query(GET_ORDERS_BY_DATE, new OrderMapper(), startDate, endDate);
        for (Order order : orders) {
            order.setUser(getUserForOrder(order));
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate, User user) {
        final String GET_ORDERS_BY_DATE = "SELECT * FROM OrderTable "
                + "WHERE userId = ? AND datetime BETWEEN ? and ?";
        List<Order> orders = jdbc.query(GET_ORDERS_BY_DATE, new OrderMapper(), user.getId(), startDate, endDate);
        for (Order order : orders) {
            order.setUser(getUserForOrder(order));
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByType(boolean isSell) {
        final String GET_ORDERS_BY_TYPE = "SELECT * FROM OrderTable "
                + "WHERE type = ?";
        int type = isSell ? 1 : 0;
        List<Order> orders = jdbc.query(GET_ORDERS_BY_TYPE, new OrderMapper(), type);
        for (Order order : orders) {
            order.setUser(getUserForOrder(order));
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByType(boolean isSell, User user) {
        final String GET_ORDERS_BY_TYPE = "SELECT * FROM OrderTable "
                + "WHERE userId = ? AND type = ?";
        int type = isSell ? 1 : 0;
        List<Order> orders = jdbc.query(GET_ORDERS_BY_TYPE, new OrderMapper(), user.getId(), type);
        for (Order order : orders) {
            order.setUser(getUserForOrder(order));
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByCompletion(boolean isComplete) {
        final String GET_ORDERS_BY_COMPLETION = "SELECT * FROM OrderTable "
                + "WHERE completed = ?";
        int completed = isComplete ? 1 : 0;
        List<Order> orders = jdbc.query(GET_ORDERS_BY_COMPLETION, new OrderMapper(), completed);
        for (Order order : orders) {
            order.setUser(getUserForOrder(order));
        }
        return orders;
    }
    
    @Override
    public List<Order> getOrdersByCompletion(boolean isComplete, User user) {
        final String GET_ORDERS_BY_COMPLETION = "SELECT * FROM OrderTable "
                + "WHERE userId = ? AND completed = ?";
        int completed = isComplete ? 1 : 0;
        List<Order> orders = jdbc.query(GET_ORDERS_BY_COMPLETION, new OrderMapper(), user.getId(), completed);
        for (Order order : orders) {
            order.setUser(getUserForOrder(order));
        }
        return orders;
    }

    private User getUserForOrder(Order order) {
        final String GET_USER_FOR_ORDER = "SELECT u.id, u.username, u.password, u.coin, u.dollars FROM User u "
                + "JOIN OrderTable o ON u.id = o.userId WHERE o.id = ?";
        User user = jdbc.queryForObject(GET_USER_FOR_ORDER, new UserMapper(), order.getId());
        return user;
    }

    /**
     * RowMapper of Orders.
     */
    public static final class OrderMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int i) throws SQLException {
            Order order = new Order();

            // ID
            order.setId(rs.getInt("id"));

            // Type
            int type = rs.getInt("type");
            if (type == 1) {
                order.setIsSell(true);
            } else {
                order.setIsSell(false);
            }

            // Price
            order.setPrice(rs.getBigDecimal("price"));

            // Amount
            order.setAmount(rs.getBigDecimal("amount"));

            // DateTime
            order.setDateTime(rs.getTimestamp("datetime").toLocalDateTime());

            // IsComplete
            int completed = rs.getInt("completed");
            if (completed == 1) {
                order.setIsComplete(true);
            } else {
                order.setIsComplete(false);
            }

            return order;
        }

    }
}
