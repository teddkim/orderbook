/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.controller;

import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.Trade;
import com.t4.orderbook.entities.User;
import com.t4.orderbook.service.OrderbookServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TaehyunKim
 */
@RestController
@RequestMapping("/orderbook")
public class TradeController {

    @Autowired
    OrderbookServiceImpl service;

    @GetMapping("/trades")
    public List<Trade> getAllTrades() {
        return service.getAllTrades();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trade createTrade(@RequestBody Order buy, Order sell) {
        return service.createTrade(buy, sell);
    }

    @GetMapping("/trade/{id}")
    public ResponseEntity<?> getTradeById(@PathVariable int id) {
        Trade trade = service.getTradeById(id);
        if (trade == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(trade);
    }

    @GetMapping("/users/{userId}/trades")
    public List<Trade> getTradesByUser(@PathVariable int userId) {
        User user = service.getUserById(userId);
        return service.getAllTradesForUser(user);
    }

    @GetMapping("/trade/query/dates")
    public List<Trade> getTradeByDateRange(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return service.getTradesByDateRange(startDate, endDate);
    }

    @GetMapping("/trade/query/prices")
    public List<Trade> getTradeByPrices(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        BigDecimal startPrice = new BigDecimal(start);
        BigDecimal endPrice = new BigDecimal(end);
        return service.getTradesByPriceRange(startPrice, endPrice);
    }

    @GetMapping("/trade/query/amounts")
    public List<Trade> getTradeByAmounts(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        BigDecimal startAmount = new BigDecimal(start);
        BigDecimal endAmount = new BigDecimal(end);
        return service.getTradesByAmountRange(startAmount, endAmount);
    }

    @GetMapping("/trade/query/amount_user")
    public List<Trade> getTradeByAmountUser(
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestBody User user) {

        BigDecimal startAmount = new BigDecimal(start);
        BigDecimal endAmount = new BigDecimal(end);
        return service.getTradesByAmountRangeAndUser(startAmount, endAmount, user);
    }

    @GetMapping("/trade/query/price_user")
    public List<Trade> getTradeByPriceUser(
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestBody User user
    ) {
        BigDecimal startPrice = new BigDecimal(start);
        BigDecimal endPrice = new BigDecimal(end);
        return service.getTradesByPriceRangeAndUser(startPrice, endPrice, user);
    }

    @GetMapping("/trade/query/date_user")
    public List<Trade> getTradeByDateUser(
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestBody User user
    ) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return service.getTradesByDateRangeAndUser(startDate, endDate, user);
    }

    @GetMapping("/trades/graph")
    public List<Trade> getDataPoints() {
        return service.getDataPoints();
    }

    @GetMapping("/trades/graph/week")
    public List<Trade> getDataPoinstForWeek() {
        return service.getDataPointsForWeek();
    }

    @GetMapping("/trade/{userId}/ten_trades")
    public List<Trade> getTenTrades(@PathVariable int userId) {
        User user = service.getUserById(userId);
        return service.getTenTrades(user);
    }
}
