/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.controller;

import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.User;
import com.t4.orderbook.service.OrderbookService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * @author Owner
 */
@RestController
@RequestMapping("/orderbook")
public class OrderController {

    @Autowired
    OrderbookService service;

    @GetMapping("/orders")
    List<Order> orders() {
        return service.getAllOrders();
    }

    @GetMapping("/orders/buys")
    List<Order> buyOrders() {
        return service.getSortedBuys();
    }
    
    @GetMapping("/orders/sells")
    List<Order> sellOrders() {
        return service.getSortedSells();
    }

    @GetMapping("/orders/date") // /orders?start="2020-09-17T00:00:00"&end="2020-09-17T23:59:59"
    List<Order> ordersByDate(
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return service.getOrdersByDate(startDate, endDate);
    }

    @GetMapping("/orders/completion")
    List<Order> ordersbyCompletion(@RequestParam boolean completed) {
        return service.getOrdersByCompletion(completed);
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<?> getOrder(@PathVariable int id) {
        Order order = service.getOrderById(id);
        if (order == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping("/createOrder/{userId}/{isSell}/{price}/{amount}")
    ResponseEntity<Order> createOrder(@PathVariable int userId, @PathVariable boolean isSell, @PathVariable BigDecimal price, @PathVariable BigDecimal amount ) throws URISyntaxException {
        User user = service.getUserById(userId);
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setPrice(price);
        newOrder.setAmount(amount);
        newOrder.setDateTime(LocalDateTime.now());
        newOrder.setIsComplete(false);
        newOrder.setIsSell(isSell);
        Order result = service.createOrder(newOrder);
        return ResponseEntity.created(new URI("/orders/" + result.getId()))
                .body(result);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity updateOrder(
            @PathVariable int id,
            @RequestBody Order order) {
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if (id != order.getId()) {
            response = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (order == service.updateOrder(order)) {
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        Order order = service.getOrderById(id);
        if (service.deleteOrder(order)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}/userorders")
    public List<Order> getUserOrders(@PathVariable int userId) {
        User user = service.getUserById(userId);
        return service.getOrdersByUser(user);
    }

    @GetMapping("/user/{userId}/dateorders")
    List<Order> ordersByDateAndUser(
            @PathVariable int userId,
            @RequestParam("start") String start,
            @RequestParam("end") String end) {
        User user = service.getUserById(userId);
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return service.getOrdersByDate(startDate, endDate, user);
    }

    @GetMapping("/user/{userId}/completionorders")
    List<Order> ordersByCompletionAndUser(
            @PathVariable int userId,
            @RequestParam("completed") boolean completed) {
        User user = service.getUserById(userId);
        return service.getOrdersByCompletion(completed, user);
    }
    
    @GetMapping("user/{userId}/orders/buys")
    List<Order> buyOrdersByUser(@PathVariable int userId) {
        User user = service.getUserById(userId);
        return service.getBuyOrdersForUser(user);
    }

    @GetMapping("user/{userId}/orders/sells")
    List<Order> sellOrdersByUser(@PathVariable int userId) {
        User user = service.getUserById(userId);
        return service.getSellOrdersForUser(user);
    }

    @GetMapping("/bidprice")
    BigDecimal getBidPrice(){
        Order order = service.getBid();
        return order.getPrice();
    }
    
    @GetMapping("/askprice")
    BigDecimal getAskPrice(){
        Order order = service.getAsk();
        return order.getPrice();
    }
}
