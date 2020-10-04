/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 *
 * @author Owner
 */
public class User {

    private int id;

    private String username;

    private String password;

    private BigDecimal coin;
    
    private BigDecimal dollars;
    
    public User() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getCoin() {
        return coin.setScale(5, RoundingMode.HALF_UP);
    }

    public void setCoin(BigDecimal coin) {
        this.coin = coin.setScale(5, RoundingMode.HALF_UP);
    }

    public BigDecimal getDollars() {
        return dollars.setScale(5, RoundingMode.HALF_UP);
    }

    public void setDollars(BigDecimal dollars) {
        this.dollars = dollars.setScale(5, RoundingMode.HALF_UP);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.username);
        hash = 53 * hash + Objects.hashCode(this.password);
        hash = 53 * hash + Objects.hashCode(this.coin);
        hash = 53 * hash + Objects.hashCode(this.dollars);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.coin, other.coin)) {
            return false;
        }
        if (!Objects.equals(this.dollars, other.dollars)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", coin=" + coin + ", dollars=" + dollars + '}';
    }
   
    

}
