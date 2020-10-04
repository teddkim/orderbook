/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.service;

import com.t4.orderbook.dao.*;
import com.t4.orderbook.entities.OpeningPrices;
import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.Share;
import com.t4.orderbook.entities.Trade;
import com.t4.orderbook.entities.User;
import java.util.Random;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kennethan
 */
@Service
public class OrderbookServiceImpl implements OrderbookService {

    @Autowired
    UserDao userDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    TradeDao tradeDao;

    @Autowired
    ShareDao shareDao;

    @Autowired
    OpeningPricesDao openingPricesDao;

    public OrderbookServiceImpl(UserDao userDao, OrderDao orderDao, TradeDao tradeDao, ShareDao shareDao, OpeningPricesDao openingPricesDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.tradeDao = tradeDao;
        this.shareDao = shareDao;
        this.openingPricesDao = openingPricesDao;
    }

    @Override
    public Order createRandomBuyOrder() {
        Random rand = new Random();
        BigDecimal rndPrice = new BigDecimal(rand.nextGaussian() * 100 + 950);
        User user = userDao.getUserById(rand.nextInt(3) + 3); // 3, 4, 5 are fake users
        BigDecimal rndAmount = new BigDecimal(rand.nextDouble() * 10 + 10);

        Order order = new Order();
        order.setUser(user);
        order.setPrice(rndPrice);
        order.setAmount(rndAmount);
        order.setIsSell(false);
        order.setIsComplete(false);
        order.setDateTime(LocalDateTime.now());

        orderDao.addOrder(order);

        checkForMatchAndCreateTrade();
        return order;
    }

    @Override
    public Order createRandomSellOrder() {
        Random rand = new Random();
        BigDecimal rndPrice = new BigDecimal(rand.nextGaussian() * 100 + 950);
        User user = userDao.getUserById(rand.nextInt(3) + 3); // 3, 4, 5 are fake users
        BigDecimal rndAmount = new BigDecimal(rand.nextDouble() * 10 + 10);

        Order order = new Order();
        order.setUser(user);
        order.setPrice(rndPrice);
        order.setAmount(rndAmount);
        order.setIsSell(true);
        order.setIsComplete(false);
        order.setDateTime(LocalDateTime.now());

        orderDao.addOrder(order);

        checkForMatchAndCreateTrade();
        return order;
    }

    @Override
    public Order createOrder(Order order) {
        Order addedOrder = orderDao.addOrder(order);
        checkForMatchAndCreateTrade();
        return addedOrder;
    }

    @Override
    public Order updateOrder(Order order) {
        Order updatedOrder = getOrderById(order.getId());
        if (!order.getIsComplete()) {
            orderDao.updateOrder(order);
            updatedOrder = getOrderById(order.getId());
        }
        checkForMatchAndCreateTrade();
        return updatedOrder;
    }

    @Override
    public boolean deleteOrder(Order order) {
        if (!order.getIsComplete()) {
            return orderDao.deleteOrderById(order.getId());
        } else {
            return false;
        }
    }

    @Override
    public Order getAsk() {
        List<Order> orders = this.getSellOrders();
        orders.sort(orderPriceComparator());
        if (orders.size() > 0) {
            return orders.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Order getBid() {
        List<Order> orders = this.getBuyOrders();
        orders.sort(orderPriceComparator().reversed());
        if (orders.size() > 0) {
            return orders.get(0);
        } else {
            return null;
        }
    }

    /**
     * A comparator that compares order prices.
     *
     * @return Comparator<Order> that compares order prices
     */
    private Comparator<Order> orderPriceComparator() {
        Comparator<Order> orderComparator = (o1, o2) -> o1.getPrice().compareTo(o2.getPrice());
        return orderComparator;
    }

    private void checkForMatchAndCreateTrade() {
        Order bid = getBid();
        Order ask = getAsk();

        if (bid != null && ask != null && bid.getPrice().compareTo(ask.getPrice()) >= 0) {
            bid.setPrice(ask.getPrice());
            createTrade(bid, ask);
        }
    }

    @Override
    public Trade createTrade(Order buy, Order sell) {
        //
        BigDecimal buyAmount = buy.getAmount();
        BigDecimal sellAmount = sell.getAmount();
        BigDecimal tradeAmount = BigDecimal.ZERO;
        switch (buyAmount.compareTo(sellAmount)) {
            case 0:
                buy.setIsComplete(true);
                sell.setIsComplete(true);
                tradeAmount = buyAmount;
                break;
            case 1: //buyAmount>sellAmount
                sell.setIsComplete(true);
                buy.setAmount(sellAmount);
                buy.setIsComplete(true);

                BigDecimal newBuyAmount = buyAmount.subtract(sellAmount);

                Order newBuyOrder = new Order();
                newBuyOrder.setPrice(buy.getPrice());
                newBuyOrder.setAmount(newBuyAmount);
                newBuyOrder.setIsComplete(false);
                newBuyOrder.setUser(buy.getUser());
                newBuyOrder.setDateTime(buy.getDateTime());
                newBuyOrder = orderDao.addOrder(newBuyOrder);

                tradeAmount = sellAmount;
                break;
            case -1:
                buy.setIsComplete(true);
                sell.setAmount(buyAmount);
                sell.setIsComplete(true);

                BigDecimal newSellAmount = sellAmount.subtract(buyAmount);

                Order newSellOrder = new Order();
                newSellOrder.setPrice(sell.getPrice());
                newSellOrder.setAmount(newSellAmount);
                newSellOrder.setIsComplete(false);
                newSellOrder.setUser(sell.getUser());
                newSellOrder.setDateTime(sell.getDateTime());
                newSellOrder = orderDao.addOrder(newSellOrder);

                tradeAmount = buyAmount;
                break;
            default:
                System.out.println("this should not happen");
            // throw error or exception?
        }
        orderDao.updateOrder(buy);
        orderDao.updateOrder(sell);

        // new order created with new id?
        Trade trade = new Trade();
        trade.setAmount(tradeAmount);
        trade.setPrice(sell.getPrice());
        trade.setBuyOrder(buy);
        trade.setSellOrder(sell);
        if (buy.getDateTime().isAfter(sell.getDateTime())) {
            trade.setDatetime(buy.getDateTime());
        } else {
            trade.setDatetime(sell.getDateTime());
        }

        trade = tradeDao.addTrade(trade);

        BigDecimal tradePrice = sell.getPrice();
        Share share = shareDao.getShareById(1);
        share.setPrice(tradePrice);
        share.setPercentage(this.getPercentage(trade.getDatetime().withHour(0).withMinute(0).withSecond(0), 1, trade));
        if (tradePrice.compareTo(share.getHigh()) > 0) {
            share.setHigh(tradePrice);
        }
        if (tradePrice.compareTo(share.getLow()) < 0) {
            share.setLow(tradePrice);
        }
        shareDao.updateShare(share);

        return trade;

    }

    @Override
    public List<Order> getBuyOrders() {
        return orderDao.getOrdersByType(false).stream().filter((o) -> !o.getIsComplete()).collect(Collectors.toList());
    }

    @Override
    public List<Order> getSellOrders() {
        return orderDao.getOrdersByType(true).stream().filter((o) -> !o.getIsComplete()).collect(Collectors.toList());
    }

    @Override
    public List<Order> getBuyOrdersForUser(User user) {
        return orderDao.getOrdersByType(false, user).stream().filter((o) -> !o.getIsComplete()).collect(Collectors.toList());
    }

    @Override
    public List<Order> getSellOrdersForUser(User user) {
        return orderDao.getOrdersByType(true, user).stream().filter((o) -> !o.getIsComplete()).collect(Collectors.toList());
    }

    @Override
    public Order getOrderById(int id) {
        return orderDao.getOrderById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderDao.getOrdersByUser(user);
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return orderDao.getOrdersByDate(startDate, endDate);
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate, User user) {
        return orderDao.getOrdersByDate(startDate, endDate, user);
    }

    @Override
    public List<Order> getOrdersByCompletion(boolean isComplete) {
        return orderDao.getOrdersByCompletion(isComplete);
    }

    @Override
    public List<Order> getOrdersByCompletion(boolean isComplete, User user) {
        return orderDao.getOrdersByCompletion(isComplete, user);
    }

    @Override
    public Trade getTradeById(int id) {
        return tradeDao.getTradeById(id);
    }

    @Override
    public List<Trade> getAllTrades() {
        return tradeDao.getAllTrades();
    }

    @Override
    public List<Trade> getAllTradesForUser(User user) {
        return tradeDao.getAllTradesForUser(user);
    }

    @Override
    public void deleteTradeById(int id) {
        tradeDao.deleteTradeById(id);
    }

    @Override
    public List<Trade> getTradesByDateRange(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        return tradeDao.getTradesByDateRange(startDatetime, endDatetime);
    }

    @Override
    public List<Trade> getTradesByDateRangeAndUser(LocalDateTime startDatetime, LocalDateTime endDatetime, User user) {
        return tradeDao.getTradesByDateRangeAndUser(startDatetime, endDatetime, user);
    }

    @Override
    public List<Trade> getTradesByPriceRange(BigDecimal startPrice, BigDecimal endPrice) {
        return tradeDao.getTradesByPriceRange(startPrice, endPrice);
    }

    @Override
    public List<Trade> getTradesByPriceRangeAndUser(BigDecimal startPrice, BigDecimal endPrice, User user) {
        return tradeDao.getTradesByPriceRangeAndUser(startPrice, endPrice, user);
    }

    @Override
    public List<Trade> getTradesByAmountRange(BigDecimal startAmount, BigDecimal endAmount) {
        return tradeDao.getTradesByAmountRange(startAmount, endAmount);
    }

    @Override
    public List<Trade> getTradesByAmountRangeAndUser(BigDecimal startAmount, BigDecimal endAmount, User user) {
        return tradeDao.getTradesByAmountRangeAndUser(startAmount, endAmount, user);
    }

    @Override
    public OpeningPrices getOpeningPrice(LocalDateTime datetime, int shareId) {
        return openingPricesDao.getOpeningPrice(datetime, shareId);
    }

    @Override
    public List<OpeningPrices> getAllOpeningPrices() {
        return openingPricesDao.getAllOpeningPrices();
    }

    @Override
    public OpeningPrices addOpeningPrices(OpeningPrices openingPrices) {
        return openingPricesDao.addOpeningPrices(openingPrices);
    }

    @Override
    public void deleteOpeningPrices(LocalDateTime datetime, int shareId) {
        openingPricesDao.deleteOpeningPrices(datetime, shareId);
    }

    @Override
    public Share getShareById(int id) {
        return shareDao.getShareById(id);
    }

    @Override
    public List<Share> getAllShares() {
        return shareDao.getAllShares();
    }

    @Override
    public Share addShare(Share share) {
        return shareDao.addShare(share);
    }

    @Override
    public Share updateShare(Share share) {
        Share updatedShare = getShareById(share.getId());
        if (!shareDao.updateShare(share)) {
            updatedShare = getShareById(share.getId());
        }
        return updatedShare;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public boolean deleteUserById(int id) {
        return userDao.deleteUserById(id);
    }

    @Override
    public BigDecimal getPercentage(LocalDateTime datetime, int shareId, Trade trade) {
        OpeningPrices op = openingPricesDao.getOpeningPrice(datetime, shareId);
        BigDecimal openingPrice = op.getPrice();

        BigDecimal latestPrice = trade.getPrice();

        BigDecimal percentage = latestPrice.subtract(openingPrice);
        percentage = percentage.divide(openingPrice);

        return percentage;
    }

    @Override
    public List<Order> getSortedSells() {
        List<Order> orders = getSellOrders();
        orders.sort(orderPriceComparator());
        return orders;
    }

    @Override
    public List<Order> getSortedBuys() {
        List<Order> orders = getBuyOrders();
        orders.sort(orderPriceComparator().reversed());
        return orders;
    }

    @Override
    public List<Trade> getDataPoints() {
        List<OpeningPrices> ops = openingPricesDao.getAllOpeningPrices();
        OpeningPrices op = ops.get(ops.size() - 1);

        LocalDateTime startDateTime = LocalDate.now().atTime(9, 30, 0);
        LocalDateTime endDateTime = LocalDateTime.now();
        if (endDateTime.compareTo(LocalDate.now().atTime(16, 0, 0)) > 0) {
            endDateTime = LocalDate.now().atTime(16, 0, 0);
        }

        List<Trade> dataPoints = new ArrayList<>();
        Trade emptyTrade = new Trade();
        emptyTrade.setId(0);
        emptyTrade.setPrice(op.getPrice());
        emptyTrade.setAmount(BigDecimal.ONE);
        emptyTrade.setDatetime(startDateTime);
        dataPoints.add(emptyTrade);

        LocalDateTime temp, low;
        LocalDateTime high = startDateTime;

        while (high.compareTo(endDateTime) < 0) {
            temp = dataPoints.get(dataPoints.size() - 1).getDatetime();
            low = LocalDateTime.of(temp.getYear(), temp.getMonth(), temp.getDayOfMonth(), temp.getHour(), temp.getMinute(), temp.getSecond());
            high = temp.plusSeconds(30);

            List<Trade> tradesInHour = tradeDao.getTradesByDateRange(low, high);
            Trade lastTrade = new Trade();
            Trade tempTrade = dataPoints.get(dataPoints.size() - 1);
            lastTrade.setId(tempTrade.getId());
            lastTrade.setPrice(tempTrade.getPrice());
            lastTrade.setAmount(tempTrade.getAmount());
            if (tempTrade.getBuyOrder() != null) {
                lastTrade.setBuyOrder(tempTrade.getBuyOrder());
            }
            if (tempTrade.getSellOrder() != null) {
                lastTrade.setSellOrder(tempTrade.getSellOrder());
            }

            if (tradesInHour.size() > 0) {
                lastTrade.setDatetime(high);
                lastTrade.setPrice(tradesInHour.get(0).getPrice());
                dataPoints.add(lastTrade);
            } else {
                lastTrade.setDatetime(high);
                dataPoints.add(lastTrade);
            }
        }

        return dataPoints;
    }

    @Override
    public List<Trade> getTenTrades(User user) {
        return tradeDao.getLastTenTrades(user);
    }

    @Override
    public List<Trade> getDataPointsForWeek() {
        List<OpeningPrices> ops = openingPricesDao.getAllOpeningPrices();
        OpeningPrices op = ops.get(ops.size() - 1);

//        LocalDateTime startDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).minusDays(7);
        LocalDateTime startDateTime = LocalDate.now().minusDays(7).atTime(9, 30, 0);
        LocalDateTime endDateTime = LocalDateTime.now();
        if (endDateTime.compareTo(LocalDate.now().atTime(16, 0, 0)) > 0) {
            endDateTime = LocalDate.now().atTime(16, 0, 0);
        }

        List<Trade> dataPoints = new ArrayList<>();
        Trade emptyTrade = new Trade();
        emptyTrade.setId(0);
        emptyTrade.setPrice(op.getPrice());
        emptyTrade.setAmount(BigDecimal.ONE);
        emptyTrade.setDatetime(startDateTime);
        dataPoints.add(emptyTrade);

        LocalDateTime temp, low;
        LocalDateTime high = startDateTime;

        while (high.compareTo(endDateTime) < 0) {
            temp = dataPoints.get(dataPoints.size() - 1).getDatetime();
            low = LocalDateTime.of(temp.getYear(), temp.getMonth(), temp.getDayOfMonth(), temp.getHour(), temp.getMinute(), temp.getSecond());
            if (low.equals(temp.withHour(16).withMinute(0).withSecond(0))) {
                high = temp.plusDays(1).withHour(9).withMinute(30).withSecond(0);
            } else {
                high = temp.plusMinutes(10);
            }

            List<Trade> tradesInHour = tradeDao.getTradesByDateRange(low, high);
            Trade lastTrade = new Trade();
            Trade tempTrade = dataPoints.get(dataPoints.size() - 1);
            lastTrade.setId(tempTrade.getId());
            lastTrade.setPrice(tempTrade.getPrice());
            lastTrade.setAmount(tempTrade.getAmount());
            if (tempTrade.getBuyOrder() != null) {
                lastTrade.setBuyOrder(tempTrade.getBuyOrder());
            }
            if (tempTrade.getSellOrder() != null) {
                lastTrade.setSellOrder(tempTrade.getSellOrder());
            }

            if (tradesInHour.size() > 0) {
                lastTrade.setDatetime(high);
                lastTrade.setPrice(tradesInHour.get(0).getPrice());
                dataPoints.add(lastTrade);
            } else {
                lastTrade.setDatetime(high);
                dataPoints.add(lastTrade);
            }
        }

        return dataPoints;
    }

}
