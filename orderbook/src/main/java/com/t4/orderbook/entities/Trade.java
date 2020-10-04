/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author AmanK
 */
public class Trade {

    private int id;

    private LocalDateTime datetime;

    private BigDecimal price;

    private BigDecimal amount;

    private Order buyOrder;

    private Order sellOrder;

    public Trade() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getPrice() {
        return price.setScale(5, RoundingMode.HALF_UP);
    }

    public void setPrice(BigDecimal Price) {
        this.price = Price.setScale(5, RoundingMode.HALF_UP);
    }

    public BigDecimal getAmount() {
        return amount.setScale(5, RoundingMode.HALF_UP);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(5, RoundingMode.HALF_UP);
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(Order buyOrder) {
        this.buyOrder = buyOrder;
    }

    public Order getSellOrder() {
        return sellOrder;
    }

    public void setSellOrder(Order sellOrder) {
        this.sellOrder = sellOrder;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.datetime);
        hash = 13 * hash + Objects.hashCode(this.price);
        hash = 13 * hash + Objects.hashCode(this.amount);
        hash = 13 * hash + Objects.hashCode(this.buyOrder);
        hash = 13 * hash + Objects.hashCode(this.sellOrder);
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
        final Trade other = (Trade) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.datetime, other.datetime)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.buyOrder, other.buyOrder)) {
            return false;
        }
        if (!Objects.equals(this.sellOrder, other.sellOrder)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "Trade{" + "id=" + id + ", datetime=" + datetime + ", Price=" + price + ", amount=" + amount + ", buyOrder=" + buyOrder + ", sellOrder=" + sellOrder + '}';
    }

}
