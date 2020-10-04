/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.OpeningPrices;
import com.t4.orderbook.entities.Share;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author TaehyunKim
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OpeningPricesDaoDBTest {
    
    @Autowired
    OpeningPricesDao openingPricesDao;
    
    @Autowired
    ShareDao shareDao;
    
    public OpeningPricesDaoDBTest() {
    }
    
    @BeforeEach
    public void setUp() {
        List<OpeningPrices> ops = openingPricesDao.getAllOpeningPrices();
        for(OpeningPrices op : ops) {
            openingPricesDao.deleteOpeningPrices(op.getDatetime(), op.getShareId());
        }
        
        List<Share> shares = shareDao.getAllShares();
        for(Share share : shares) {
            shareDao.deleteShare(share.getId());
        }    
    }

    /**
     * Test of getOpeningPricesById method, of class OpeningPricesDaoDB.
     */
    @Test
    public void testAddAndGetOpeningPricesById() {
        BigDecimal tickSize = new BigDecimal("0.05");
        BigDecimal percentage = new BigDecimal("0.01");
        BigDecimal high = new BigDecimal("11000.00");
        BigDecimal low = new BigDecimal("9000.00");
        BigDecimal price = new BigDecimal("10000.03");
        
        Share share = new Share();
        share.setName("test");
        share.setSymbol("test");
        share.setTickSize(tickSize);
        share.setPercentage(percentage);
        share.setHigh(high);
        share.setLow(low);
        share.setPrice(price);
        
        share = shareDao.addShare(share);
        
        OpeningPrices op = new OpeningPrices();
        
        LocalDateTime myDateObj = LocalDateTime.now();
        myDateObj = LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
  
        op.setDatetime(myDateObj);
        op.setPrice(price);
        op.setShareId(share.getId());
        
        op = openingPricesDao.addOpeningPrices(op);
        
        OpeningPrices fromDao = openingPricesDao.getOpeningPrice(myDateObj, share.getId());
        
        assertEquals(op, fromDao);
    }

    /**
     * Test of getAllOpeningPrices method, of class OpeningPricesDaoDB.
     */
    @Test
    public void testGetAllOpeningPrices() {
        BigDecimal tickSize = new BigDecimal("0.05");
        BigDecimal percentage = new BigDecimal("0.01");
        BigDecimal high = new BigDecimal("11000.00");
        BigDecimal low = new BigDecimal("9000.00");
        BigDecimal price = new BigDecimal("10000.03");
        
        Share share = new Share();
        share.setName("test");
        share.setSymbol("test");
        share.setTickSize(tickSize);
        share.setPercentage(percentage);
        share.setHigh(high);
        share.setLow(low);
        share.setPrice(price);
        
        share = shareDao.addShare(share);
        
        OpeningPrices op = new OpeningPrices();
        
        LocalDateTime myDateObj = LocalDateTime.now();
        myDateObj = LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
  
        op.setDatetime(myDateObj);
        op.setPrice(price);
        op.setShareId(share.getId());
        
        op = openingPricesDao.addOpeningPrices(op);
        
        OpeningPrices op2 = new OpeningPrices();
        
        LocalDateTime myDateObj2 = LocalDateTime.now();
        myDateObj2 = LocalDateTime.parse("15-09-2020 12:12:12", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
  
        op2.setDatetime(myDateObj2);
        op2.setPrice(price);
        op2.setShareId(share.getId());
        
        op2 = openingPricesDao.addOpeningPrices(op2);
        
        List<OpeningPrices> ops = openingPricesDao.getAllOpeningPrices();
        
        assertEquals(2, ops.size());
        assertTrue(ops.contains(op));
        assertTrue(ops.contains(op2));
    }

    /**
     * Test of delteOpeningPrices method, of class OpeningPricesDaoDB.
     */
    @Test
    public void testDelteOpeningPrices() {
        BigDecimal tickSize = new BigDecimal("0.05");
        BigDecimal percentage = new BigDecimal("0.01");
        BigDecimal high = new BigDecimal("11000.00");
        BigDecimal low = new BigDecimal("9000.00");
        BigDecimal price = new BigDecimal("10000.03");
        
        Share share = new Share();
        share.setName("test");
        share.setSymbol("test");
        share.setTickSize(tickSize);
        share.setPercentage(percentage);
        share.setHigh(high);
        share.setLow(low);
        share.setPrice(price);
        
        share = shareDao.addShare(share);
        
        OpeningPrices op = new OpeningPrices();
        
        LocalDateTime myDateObj = LocalDateTime.now();
        myDateObj = LocalDateTime.parse("15-09-2020 11:11:11", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
  
        op.setDatetime(myDateObj);
        op.setPrice(price);
        op.setShareId(share.getId());
        
        op = openingPricesDao.addOpeningPrices(op);
        
        OpeningPrices fromDao = openingPricesDao.getOpeningPrice(myDateObj, share.getId());
        assertEquals(op, fromDao);
        
        openingPricesDao.deleteOpeningPrices(myDateObj, op.getShareId());
        fromDao = openingPricesDao.getOpeningPrice(myDateObj, share.getId());
        
        assertNull(fromDao);      
    }
    
}
