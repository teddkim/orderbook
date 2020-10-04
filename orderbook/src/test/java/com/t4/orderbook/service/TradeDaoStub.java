/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.service;

import com.t4.orderbook.dao.TradeDao;
import com.t4.orderbook.entities.Trade;
import com.t4.orderbook.entities.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AmanK
 */
public class TradeDaoStub implements TradeDao {

    List<Trade> trades = new ArrayList<>();
    int currentId = 1;
    
    @Override
    public Trade getTradeById(int id) {
        for (Trade trade : trades) {
            if (trade.getId() == id) {
                Trade newTrade = new Trade();
                newTrade.setId(id);
                newTrade.setAmount(trade.getAmount());
                newTrade.setDatetime(trade.getDatetime());
                newTrade.setPrice(trade.getPrice());
                newTrade.setBuyOrder(trade.getBuyOrder());
                newTrade.setSellOrder(trade.getSellOrder());
                return newTrade;
            }
        }
        return null;
    }

    @Override
    public List<Trade> getAllTrades() {
        return trades;
    }

    @Override
    public List<Trade> getAllTradesForUser(User user) {
        List<Trade> tradesForUser = new ArrayList<>();
        for (Trade trade : trades) {
            if (trade.getBuyOrder().getUser() == user || trade.getSellOrder().getUser() == user) {
                tradesForUser.add(trade);
            }
        }
        return tradesForUser;
    }

    @Override
    public void deleteTradeById(int id) { // CHANGE THIS TO BOOLEAN

        trades.remove(getTradeById(id));
    }

    @Override
    public void updateTrade(Trade trade) {  // CHANGE THIS TO BOOLEAN
        for (Trade tradeInList : trades) {
            if (tradeInList.getId() == trade.getId()) {
                tradeInList.setAmount(trade.getAmount());
                tradeInList.setPrice(trade.getPrice());
                tradeInList.setDatetime(trade.getDatetime());
                //return true;
            }
        }
        //return false;
    }

    @Override
    public Trade addTrade(Trade trade) {
        trade.setId(currentId);
        trades.add(trade);
        currentId++;
        return trade;
    }

    @Override
    public List<Trade> getTradesByDateRange(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        List<Trade> tradesInList = new ArrayList<>();
        for (Trade trade : trades) {
            if (trade.getDatetime().isAfter(startDatetime) && trade.getDatetime().isBefore(endDatetime)) {
                tradesInList.add(trade);
            }
        }
        return tradesInList;
    }

    @Override
    public List<Trade> getTradesByDateRangeAndUser(LocalDateTime startDatetime, LocalDateTime endDatetime, User user) {
        List<Trade> tradesInList = new ArrayList<>();
        for (Trade trade : trades) {
            if (trade.getDatetime().isAfter(startDatetime) && trade.getDatetime().isBefore(endDatetime) && ((trade.getBuyOrder().getUser() == user) || (trade.getSellOrder().getUser() == user))) {
                tradesInList.add(trade);
            }
        }
        return tradesInList;
    }

    @Override
    public List<Trade> getTradesByPriceRange(BigDecimal startPrice, BigDecimal endPrice) {
        List<Trade> tradesInList = new ArrayList<>();
        for (Trade trade : trades) {
            if (trade.getPrice().compareTo(endPrice) == -1 && trade.getPrice().compareTo(startPrice) == 1) {
                tradesInList.add(trade);
            }
        }
        return tradesInList;
    }

    @Override
    public List<Trade> getTradesByPriceRangeAndUser(BigDecimal startPrice, BigDecimal endPrice, User user) {
        List<Trade> tradesInList = new ArrayList<>();
        for (Trade trade : trades) {
            if ((trade.getPrice().compareTo(endPrice) == -1) && (trade.getPrice().compareTo(startPrice) == 1) && ((trade.getBuyOrder().getUser() == user) || (trade.getSellOrder().getUser() == user))) {
                tradesInList.add(trade);
            }
        }
        return tradesInList;
    }

    @Override
    public List<Trade> getTradesByAmountRange(BigDecimal startAmount, BigDecimal endAmount) {
        List<Trade> tradesInList = new ArrayList<>();
        for (Trade trade : trades) {
            if ((trade.getAmount().compareTo(startAmount) == 1) && (trade.getAmount().compareTo(endAmount) == -1) ) {
                tradesInList.add(trade);
            }
        }
        return tradesInList;
    }

    @Override
    public List<Trade> getTradesByAmountRangeAndUser(BigDecimal startAmount, BigDecimal endAmount, User user) {
        List<Trade> tradesInList = new ArrayList<>();
        for (Trade trade : trades) {
            if ((trade.getAmount().compareTo(startAmount) == +1) && (trade.getAmount().compareTo(endAmount) == -1) && ((trade.getBuyOrder().getUser() == user) || (trade.getSellOrder().getUser() == user))) {
                tradesInList.add(trade);
            }
        }
        return tradesInList;
    }

    @Override
    public List<Trade> getLastTenTrades(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
