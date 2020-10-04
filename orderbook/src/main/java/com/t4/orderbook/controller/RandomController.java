/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.controller;

import com.t4.orderbook.entities.Order;
import com.t4.orderbook.service.OrderbookService;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kennethan
 */
@RestController
@RequestMapping("/orderbook")
public class RandomController {

    @Autowired
    OrderbookService service;

    @PostMapping("/random/buy")
    Order createRandomBuyOrder() throws URISyntaxException {
        Order result = service.createRandomBuyOrder();
        return result;
    }

    @PostMapping("/random/sell")
    Order createRandomSellOrder() throws URISyntaxException {
        Order result = service.createRandomSellOrder();
        return result;
    }
}
