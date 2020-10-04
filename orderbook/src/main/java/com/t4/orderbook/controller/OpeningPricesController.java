/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.controller;

import com.t4.orderbook.entities.OpeningPrices;
import com.t4.orderbook.entities.Share;
import com.t4.orderbook.service.OrderbookService;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TaehyunKim
 */
@RestController
@RequestMapping("/orderbook")
public class OpeningPricesController {
    
    @Autowired 
    OrderbookService service;
    
    @GetMapping("/openingprices")
    List<OpeningPrices> openingPrices() {
        return service.getAllOpeningPrices();
    }
    
    @GetMapping("/openingprices/{id}") 
    ResponseEntity<?> getOpeningPrice(@PathVariable String datetime, @PathVariable int shareId){
        LocalDateTime date = LocalDateTime.parse(datetime);
        OpeningPrices op = service.getOpeningPrice(date, shareId);
        if(op == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(op);
    }
    
    @PostMapping("/openingprices") 
    ResponseEntity<OpeningPrices> createOpeningPrices(@RequestBody OpeningPrices op) throws URISyntaxException{
       OpeningPrices result = service.addOpeningPrices(op);
       return ResponseEntity.created(new URI("/openingprices/" + result.getDatetime())).body(result);
    }
    
    @GetMapping("/openingprices/percentage") 
    ResponseEntity<BigDecimal> getPercentage(@PathVariable String datetime, @PathVariable int shareId) {
        LocalDateTime date = LocalDateTime.parse(datetime);
        return ResponseEntity.ok(service.getPercentage(date, shareId));
    }
    
    @DeleteMapping("/openingprices/{id}")
    ResponseEntity<?> deleteUser(@PathVariable String datetime, @PathVariable int shareId) {
        LocalDateTime date = LocalDateTime.parse(datetime);
        service.deleteOpeningPrices(date, shareId);
        return ResponseEntity.ok().build();
    }
}
