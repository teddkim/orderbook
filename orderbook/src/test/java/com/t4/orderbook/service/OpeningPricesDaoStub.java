/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.service;

import com.t4.orderbook.dao.OpeningPricesDao;
import com.t4.orderbook.entities.OpeningPrices;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AmanK
 */
public class OpeningPricesDaoStub implements OpeningPricesDao{

    List<OpeningPrices> openingPricesList = new ArrayList<>();
    
    @Override
    public OpeningPrices getOpeningPrice(LocalDateTime datetime, int shareId) {
        for(OpeningPrices openingPrice : openingPricesList){
            if((openingPrice.getDatetime() == datetime) && (openingPrice.getShareId() == shareId)){
                return openingPrice;
            }
        }
        return null;
    }

    @Override
    public List<OpeningPrices> getAllOpeningPrices() {
        return openingPricesList;
    }

    @Override
    public OpeningPrices addOpeningPrices(OpeningPrices openingPrices) {
        
        openingPricesList.add(openingPrices);
        return openingPrices;
    }

    @Override
    public void deleteOpeningPrices(LocalDateTime datetime, int shareId) {
        OpeningPrices openingPriceToDelete = null;
        for(OpeningPrices openingPrice : openingPricesList){
            if((openingPrice.getDatetime() == datetime) && (openingPrice.getShareId() == shareId)){
                openingPriceToDelete = openingPrice;
            }
        }
        openingPricesList.remove(openingPriceToDelete);
        //return null;
    }
    
}
