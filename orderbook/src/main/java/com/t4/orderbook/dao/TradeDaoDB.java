/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.dao.OrderDaoDB.OrderMapper;
import com.t4.orderbook.dao.UserDaoDB.UserMapper;
import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.Trade;
import com.t4.orderbook.entities.User;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AmanK
 */
@Repository
public class TradeDaoDB implements TradeDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public TradeDaoDB(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    //HELPER FUNCTION
    private User getUserForOrder(Order order) {
        final String GET_USER_FOR_ORDER = "SELECT u.id, u.username, u.password, u.coin, u.dollars FROM User u "
                + "JOIN OrderTable o ON u.id = o.userId WHERE o.id = ?";
        User user = jdbc.queryForObject(GET_USER_FOR_ORDER, new UserMapper(), order.getId());
        return user;
    }

    //HELPER FUNCTION
    private Order getBuyOrderForTrade(Trade trade) {
        final String GET_BUY_ORDER_FOR_TRADE = "SELECT o.id, o.datetime, o.type, o.price, o.amount, o.completed FROM Trade t "
                + "JOIN OrderTable o ON t.buyOrder = o.id WHERE t.id = ?";
        Order buyOrder = jdbc.queryForObject(GET_BUY_ORDER_FOR_TRADE, new OrderMapper(), trade.getId());
        buyOrder.setUser(getUserForOrder(buyOrder));
        return buyOrder;
    }

    //HELPER FUNCTION
    private Order getSellOrderForTrade(Trade trade) {
        final String SELL_BUY_ORDER_FOR_TRADE = "SELECT o.id, o.datetime, o.type, o.price, o.amount, o.completed FROM Trade t "
                + "JOIN OrderTable o ON t.sellOrder = o.id WHERE t.id = ?";
        Order sellOrder = jdbc.queryForObject(SELL_BUY_ORDER_FOR_TRADE, new OrderMapper(), trade.getId());
        sellOrder.setUser(getUserForOrder(sellOrder));
        return sellOrder;
    }

    @Override
    public Trade getTradeById(int id) {
        try {
            final String SELECT_TRADE_BY_ID = "SELECT * FROM Trade WHERE ID=?;";
            Trade trade = jdbc.queryForObject(SELECT_TRADE_BY_ID, new TradeMapper(), id);
            trade.setBuyOrder(getBuyOrderForTrade(trade));
            trade.setSellOrder(getSellOrderForTrade(trade));
            return trade;

        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Trade> getAllTrades() {
        final String SELECT_ALL_TRADES = "SELECT * FROM Trade ORDER BY datetime DESC;";
        List<Trade> trades = jdbc.query(SELECT_ALL_TRADES, new TradeMapper());
        for (Trade trade : trades) {
            trade.setBuyOrder(getBuyOrderForTrade(trade));
            trade.setSellOrder(getSellOrderForTrade(trade));
        }
        return trades;
    }

    @Override
    public List<Trade> getAllTradesForUser(User user) {
        final String TRADES_FOR_USER = "SELECT t.id, t.datetime, t.price, t.amount"
                + " FROM Trade t"
                + " LEFT JOIN OrderTable ob ON t.buyOrder = ob.id"
                + " LEFT JOIN OrderTable os ON t.sellOrder = os.id"
                + " LEFT JOIN User u1 ON ob.userid = u1.id"
                + " LEFT JOIN User u2 ON os.userid = u2.id"
                + " WHERE u1.id = ? OR u2.id = ?;";
        List<Trade> trades = jdbc.query(TRADES_FOR_USER, new TradeMapper(), user.getId(), user.getId());
        for (Trade trade : trades) {
            trade.setBuyOrder(getBuyOrderForTrade(trade));
            trade.setSellOrder(getSellOrderForTrade(trade));
        }
        return trades;
    }

    @Override
    public void deleteTradeById(int id) {
        final String DELETE_TRADE_BY_ID = "DELETE FROM Trade WHERE id = ?;";
        jdbc.update(DELETE_TRADE_BY_ID, id);

    }

    @Override
    public void updateTrade(Trade trade) {
        final String UPDATE_TRADE = "UPDATE Trade SET "
                + "datetime = ?, "
                + "price = ?, "
                + "amount = ?, "
                + "buyOrder = ?, "
                + "sellOrder = ? "
                + "WHERE id = ?;";

        jdbc.update(UPDATE_TRADE,
                trade.getDatetime(),
                trade.getPrice(),
                trade.getAmount(),
                trade.getBuyOrder().getId(),
                trade.getSellOrder().getId(),
                trade.getId());
    }

    @Override
    @Transactional
    public Trade addTrade(Trade trade) {
        final String INSERT_TRADE = "INSERT INTO Trade(datetime, price, amount, buyOrder, sellOrder) VALUES(?,?,?,?,?);";

        jdbc.update(INSERT_TRADE,
                trade.getDatetime(),
                trade.getPrice(),
                trade.getAmount(),
                trade.getBuyOrder().getId(),
                trade.getSellOrder().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        trade.setId(newId);
        return trade;
    }

    @Override
    public List<Trade> getTradesByDateRange(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        final String SELECT_TRADES_BY_DATES = "SELECT * FROM Trade WHERE datetime BETWEEN ? AND ? ORDER BY datetime DESC";
        List<Trade> trades = jdbc.query(SELECT_TRADES_BY_DATES, new TradeMapper(), Timestamp.valueOf(startDatetime), endDatetime);
        for (Trade trade : trades) {
            trade.setBuyOrder(getBuyOrderForTrade(trade));
            trade.setSellOrder(getSellOrderForTrade(trade));
        }
        return trades;
    }

    @Override
    public List<Trade> getTradesByDateRangeAndUser(LocalDateTime startDatetime, LocalDateTime endDatetime, User user) {
        final String SELECT_TRADES_BY_DATES_AND_USER = "SELECT t.id, t.datetime, t.price, t.amount"
                + " FROM Trade t"
                + " LEFT JOIN OrderTable ob ON t.buyOrder = ob.id"
                + " LEFT JOIN OrderTable os ON t.sellOrder = os.id"
                + " LEFT JOIN User u1 ON ob.userid = u1.id"
                + " LEFT JOIN User u2 ON os.userid = u2.id"
                + " WHERE (u1.id = ? OR u2.id = ?) AND (t.datetime BETWEEN ? and ?);";
        List<Trade> trades = jdbc.query(SELECT_TRADES_BY_DATES_AND_USER, new TradeMapper(), user.getId(), user.getId(), startDatetime, endDatetime);
        for (Trade trade : trades) {
            trade.setBuyOrder(getBuyOrderForTrade(trade));
            trade.setSellOrder(getSellOrderForTrade(trade));
        }
        return trades;
    }

    @Override
    public List<Trade> getTradesByPriceRange(BigDecimal startPrice, BigDecimal endPrice) {
        final String SELECT_TRADES_BY_PRICES = "SELECT * FROM Trade WHERE price BETWEEN ? AND ? ORDER BY price ASC";
        List<Trade> trades = jdbc.query(SELECT_TRADES_BY_PRICES, new TradeMapper(), startPrice, endPrice);
        for (Trade trade : trades) {
            trade.setBuyOrder(getBuyOrderForTrade(trade));
            trade.setSellOrder(getSellOrderForTrade(trade));
        }
        return trades;
    }

    @Override
    public List<Trade> getTradesByPriceRangeAndUser(BigDecimal startPrice, BigDecimal endPrice, User user) {
        final String SELECT_TRADES_BY_PRICES_AND_USER = "SELECT t.id, t.datetime, t.price, t.amount"
                + " FROM Trade t"
                + " LEFT JOIN OrderTable ob ON t.buyOrder = ob.id"
                + " LEFT JOIN OrderTable os ON t.sellOrder = os.id"
                + " LEFT JOIN User u1 ON ob.userid = u1.id"
                + " LEFT JOIN User u2 ON os.userid = u2.id"
                + " WHERE (u1.id = ? OR u2.id = ?) AND (t.price BETWEEN ? and ?);";
        List<Trade> trades = jdbc.query(SELECT_TRADES_BY_PRICES_AND_USER, new TradeMapper(), user.getId(), user.getId(), startPrice, endPrice);
        for (Trade trade : trades) {
            trade.setBuyOrder(getBuyOrderForTrade(trade));
            trade.setSellOrder(getSellOrderForTrade(trade));
        }
        return trades;

    }

    @Override
    public List<Trade> getTradesByAmountRange(BigDecimal startAmount, BigDecimal endAmount) {
        final String SELECT_TRADES_BY_AMOUNT = "SELECT * FROM Trade WHERE amount BETWEEN ? AND ? ORDER BY amount ASC";
        List<Trade> trades = jdbc.query(SELECT_TRADES_BY_AMOUNT, new TradeMapper(), startAmount, endAmount);
        for (Trade trade : trades) {
            trade.setBuyOrder(getBuyOrderForTrade(trade));
            trade.setSellOrder(getSellOrderForTrade(trade));
        }
        return trades;
    }

    @Override
    public List<Trade> getTradesByAmountRangeAndUser(BigDecimal startAmount, BigDecimal endAmount, User user) {
        final String SELECT_TRADES_BY_AMOUNT_AND_USER = "SELECT t.id, t.datetime, t.price, t.amount"
                + " FROM Trade t"
                + " LEFT JOIN OrderTable ob ON t.buyOrder = ob.id"
                + " LEFT JOIN OrderTable os ON t.sellOrder = os.id"
                + " LEFT JOIN User u1 ON ob.userid = u1.id"
                + " LEFT JOIN User u2 ON os.userid = u2.id"
                + " WHERE (u1.id = ? OR u2.id = ?) AND (t.amount BETWEEN ? and ?);";
        List<Trade> trades = jdbc.query(SELECT_TRADES_BY_AMOUNT_AND_USER, new TradeMapper(), user.getId(), user.getId(), startAmount, endAmount);
        for (Trade trade : trades) {
            trade.setBuyOrder(getBuyOrderForTrade(trade));
            trade.setSellOrder(getSellOrderForTrade(trade));
        }
        return trades;
    }

    @Override
    public List<Trade> getLastTenTrades(User user) {
        final String TEN_TRADES_FOR_USER = "SELECT t.id, t.datetime, t.price, t.amount"
                + " FROM Trade t"
                + " LEFT JOIN OrderTable ob ON t.buyOrder = ob.id"
                + " LEFT JOIN OrderTable os ON t.sellOrder = os.id"
                + " LEFT JOIN User u1 ON ob.userid = u1.id"
                + " LEFT JOIN User u2 ON os.userid = u2.id"
                + " WHERE u1.id = ? OR u2.id = ?"
                + " ORDER BY datetime DESC"
                + " LIMIT 10;";
        List<Trade> trades = jdbc.query(TEN_TRADES_FOR_USER, new TradeMapper(), user.getId(), user.getId());
        for (Trade trade : trades) {
            trade.setBuyOrder(getBuyOrderForTrade(trade));
            trade.setSellOrder(getSellOrderForTrade(trade));
        }
        return trades;
    }

    public static final class TradeMapper implements RowMapper<Trade> {

        @Override
        public Trade mapRow(ResultSet rs, int index) throws SQLException {
            Trade trade = new Trade();
            trade.setId(rs.getInt("id"));
            trade.setDatetime(rs.getTimestamp("datetime").toLocalDateTime());
            trade.setPrice(rs.getBigDecimal("price"));
            trade.setAmount(rs.getBigDecimal("amount"));
            //trade.setBuyOrder(OrderDao.getOrderById(rs.getInt("buyOrder")));
            //trade.setSellOrder(OrderDao.getOrderById(rs.getInt("sellOrder")));
            return trade;
        }

    }

}
