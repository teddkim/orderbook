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
 * @author TaehyunKim
 */
public class Share {
    private int id;
    private String name;
    private String symbol;
    private BigDecimal tickSize;
    private BigDecimal percentage;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal price;

    public Share() {
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getTickSize() {
        return tickSize.setScale(5, RoundingMode.HALF_UP);
    }

    public void setTickSize(BigDecimal tickSize) {
        this.tickSize = tickSize.setScale(5, RoundingMode.HALF_UP);
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getHigh() {
        return high.setScale(5, RoundingMode.HALF_UP);
    }

    public void setHigh(BigDecimal high) {
        this.high = high.setScale(5, RoundingMode.HALF_UP);
    }

    public BigDecimal getLow() {
        return low.setScale(5, RoundingMode.HALF_UP);
    }

    public void setLow(BigDecimal low) {
        this.low = low.setScale(5, RoundingMode.HALF_UP);
    }

    public BigDecimal getPrice() {
        return price.setScale(5, RoundingMode.HALF_UP);
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(5, RoundingMode.HALF_UP);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.symbol);
        hash = 29 * hash + Objects.hashCode(this.tickSize);
        hash = 29 * hash + Objects.hashCode(this.percentage);
        hash = 29 * hash + Objects.hashCode(this.high);
        hash = 29 * hash + Objects.hashCode(this.low);
        hash = 29 * hash + Objects.hashCode(this.price);
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
        final Share other = (Share) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        if (!Objects.equals(this.tickSize, other.tickSize)) {
            return false;
        }
        if (!Objects.equals(this.percentage, other.percentage)) {
            return false;
        }
        if (!Objects.equals(this.high, other.high)) {
            return false;
        }
        if (!Objects.equals(this.low, other.low)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Share{" + "id=" + id + ", name=" + name + ", symbol=" + symbol + ", tickSize=" + tickSize + ", percentage=" + percentage + ", high=" + high + ", low=" + low + ", price=" + price + '}';
    }
    
    
}
