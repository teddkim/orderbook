package com.t4.orderbook.service;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.t4.orderbook.dao.OrderDao;
import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.User;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kennethan
 */
public class OrderDaoStub implements OrderDao {

    private List<Order> orders = new ArrayList<>();
    private int newId = 1;

    public OrderDaoStub() {
        orders = new ArrayList<>();
        newId = 1;
//        User user = new User();
//        user.setId(1);
//        user.setUsername("user1");
//        user.setPassword("password1");
//        user.setCoin(BigDecimal.ONE);
//        user.setDollars(BigDecimal.ONE);
//
//        User user2 = new User();
//        user2.setId(2);
//        user2.setUsername("user2");
//        user2.setPassword("password2");
//        user2.setCoin(new BigDecimal("2"));
//        user2.setDollars(new BigDecimal("2"));
//
//        Order order = new Order();
//        order.setUser(user);
//        order.setIsSell(false);
//        order.setPrice(BigDecimal.ONE);
//        order.setAmount(BigDecimal.ONE);
//        order.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 12, 0, 0));
//        order.setIsComplete(false);
//        orders.add(order);
//
//        Order order2 = new Order();
//        order2.setUser(user);
//        order2.setIsSell(true);
//        order2.setPrice(new BigDecimal("2"));
//        order2.setAmount(new BigDecimal("2"));
//        order2.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 12, 0, 0));
//        order2.setIsComplete(false);
//        orders.add(order2);
//
//        Order order3 = new Order();
//        order3.setUser(user2);
//        order3.setIsSell(true);
//        order3.setPrice(new BigDecimal("3"));
//        order3.setAmount(new BigDecimal("3"));
//        order3.setDateTime(LocalDateTime.of(2020, Month.SEPTEMBER, 17, 12, 0, 0));
//        order3.setIsComplete(false);
//        orders.add(order3);
    }

    @Override
    public Order getOrderById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                Order newOrder = new Order();
                newOrder.setId(order.getId());
                newOrder.setAmount(order.getAmount());
                newOrder.setDateTime(order.getDateTime());
                newOrder.setPrice(order.getPrice());
                newOrder.setIsSell(order.getIsSell());
                newOrder.setIsComplete(order.getIsComplete());
                newOrder.setUser(order.getUser());
                return newOrder;
            }
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return orders;
    }

    @Override
    public Order addOrder(Order order) {
        order.setId(newId);
        orders.add(order);
        newId++;
        return order;
    }

    @Override
    public boolean updateOrder(Order order) {
        Order fromList = getOrderById(order.getId());
        if (fromList != null) {
            fromList.setUser(order.getUser());
            fromList.setPrice(order.getPrice());
            fromList.setAmount(order.getAmount());
            fromList.setIsSell(order.getIsSell());
            fromList.setDateTime(order.getDateTime());
            fromList.setIsComplete(order.getIsComplete());
            orders.remove(order);
            orders.add(fromList);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteOrderById(int id) {
        Order order = getOrderById(id);
        if (order != null) {
            if (orders.contains(order)) {
                orders.remove(order);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUser() == user) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getDateTime().compareTo(startDate) >= 0 && order.getDateTime().compareTo(endDate) <= 0) {
                result.add(order);
            }
        }
        return result;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate, User user) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUser() == user && order.getDateTime().compareTo(startDate) >= 0 && order.getDateTime().compareTo(endDate) <= 0) {
                result.add(order);
            }
        }
        return result;
    }

    @Override
    public List<Order> getOrdersByType(boolean isSell) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getIsSell() == isSell) {
                result.add(order);
            }
        }
        return result;
    }

    @Override
    public List<Order> getOrdersByType(boolean isSell, User user) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUser() == user && order.getIsSell() == isSell) {
                result.add(order);
            }
        }
        return result;
    }

    @Override
    public List<Order> getOrdersByCompletion(boolean isComplete) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getIsComplete() == isComplete) {
                result.add(order);
            }
        }
        return result;
    }

    @Override
    public List<Order> getOrdersByCompletion(boolean isComplete, User user) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUser() == user && order.getIsComplete() == isComplete) {
                result.add(order);
            }
        }
        return result;
    }

}
