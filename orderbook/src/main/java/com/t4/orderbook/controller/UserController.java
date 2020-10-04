/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.controller;

import com.t4.orderbook.entities.User;
import com.t4.orderbook.service.OrderbookService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Owner
 */
@RestController
@RequestMapping("/orderbook")
public class UserController {

    @Autowired
    OrderbookService service;

    @GetMapping("/users")
    List<User> users() {
        return service.getAllUsers();
    }

    @GetMapping("/users/{id}")
    ResponseEntity<?> getUser(@PathVariable int id) {
        User user = service.getUserById(id);
        if (user == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
        User result = service.addUser(user);
        return ResponseEntity.created(new URI("/users/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        service.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
