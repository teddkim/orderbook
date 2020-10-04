/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.Share;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
public class ShareDaoDBTest {
    
    @Autowired
    ShareDao shareDao;
    
    public ShareDaoDBTest() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Share> shares = shareDao.getAllShares();
        for(Share share : shares) {
            shareDao.deleteShare(share.getId());
        }
    }

    /**
     * Test of getShareById method, of class ShareDaoDB.
     */
    @Test
    public void testAddAndGetShareById() {
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
        Share fromShareDao = shareDao.getShareById(share.getId());
        
        assertEquals(share.getId(), fromShareDao.getId());
        assertEquals(share.getName(), fromShareDao.getName());
        assertEquals(share.getSymbol(), fromShareDao.getSymbol());
        assertEquals(share.getTickSize(), fromShareDao.getTickSize());
        assertEquals(share.getPercentage(), fromShareDao.getPercentage());
        assertEquals(share.getHigh(), fromShareDao.getHigh());
        assertEquals(share.getLow(), fromShareDao.getLow());
        assertEquals(share.getPrice(), fromShareDao.getPrice());
        
        assertEquals(share, fromShareDao);
    }

    /**
     * Test of getAllShares method, of class ShareDaoDB.
     */
    @Test
    public void testGetAllShares() {
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
        
        Share share2 = new Share();
        share2.setName("test2");
        share2.setSymbol("test2");
        share2.setTickSize(tickSize);
        share2.setPercentage(percentage);
        share2.setHigh(high);
        share2.setLow(low);
        share2.setPrice(price);
        
        share2 = shareDao.addShare(share2);
        
        List<Share> shares = shareDao.getAllShares();
        
        assertEquals(2, shares.size());
        assertTrue(shares.contains(share));
        assertTrue(shares.contains(share2));
    }

    /**
     * Test of updateShare method, of class ShareDaoDB.
     */
    @Test
    public void testUpdateShare() {
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
        
        Share fromShareDao = shareDao.getShareById(share.getId());
        assertEquals(share, fromShareDao);
        
        share.setName("testChange");
        shareDao.updateShare(share);
        
        assertNotEquals(share, fromShareDao);
    }

    /**
     * Test of deleteShare method, of class ShareDaoDB.
     */
    @Test
    public void testDeleteShare() {
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
        Share fromShareDao = shareDao.getShareById(share.getId());
        assertEquals(share, fromShareDao);
        
        shareDao.deleteShare(share.getId());
        fromShareDao = shareDao.getShareById(share.getId());
        assertNull(fromShareDao);
    }
    
}
