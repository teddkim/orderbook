/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.OpeningPrices;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author TaehyunKim
 */
public interface OpeningPricesDao {
    OpeningPrices getOpeningPrice(LocalDateTime datetime, int shareId);
    List<OpeningPrices> getAllOpeningPrices();
    OpeningPrices addOpeningPrices(OpeningPrices openingPrices);
    void deleteOpeningPrices(LocalDateTime datetime, int shareId);
}
