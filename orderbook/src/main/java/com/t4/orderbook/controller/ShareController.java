/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.controller;

import com.t4.orderbook.entities.Share;
import com.t4.orderbook.service.OrderbookService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TaehyunKim
 */
@RestController
@RequestMapping("/orderbook")
public class ShareController {
    
    @Autowired 
    OrderbookService service;
    
    @GetMapping("/shares")
    List<Share> shares() {
        return service.getAllShares();
    }
    
    @GetMapping("/shares/{id}") 
    ResponseEntity<?> getShare(@PathVariable int id){
        Share share = service.getShareById(id);
        if(share == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(share);
    }
    
    @PostMapping("/shares") 
    ResponseEntity<Share> createShare(@RequestBody Share share) throws URISyntaxException{
       Share result = service.addShare(share);
       return ResponseEntity.created(new URI("/shares/" + result.getId())).body(result);
    }
    
    @PutMapping("/shares/{id}")
    public ResponseEntity updateShare(
            @PathVariable int id,
            @RequestBody Share share) {
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if (id != share.getId()) {
            response = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (share == service.updateShare(share)) {
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }
    
    @DeleteMapping("/shares/{id}")
    ResponseEntity<?> deleteUser(@PathVariable int id) {
        service.deleteTradeById(id);
        return ResponseEntity.ok().build();
    }
}
