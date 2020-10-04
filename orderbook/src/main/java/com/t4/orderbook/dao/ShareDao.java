/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.Share;
import java.util.List;

/**
 *
 * @author TaehyunKim
 */
public interface ShareDao {
    
    /**
     * get a share by its id. If there is no such share return null
     * @param id of the share
     * @return share if not, return null
     */
    Share getShareById(int id);
    
    /**
     * get a list of shares. If there is no share return null
     * @return list of shares
     */
    List<Share> getAllShares();
    
    /**
     * add a share to the database
     * @param share you'd like to add
     * @return Share
     */
    Share addShare(Share share);
    
    /**
     * update share
     * @param share that has been updated
     */
    boolean updateShare(Share share);
    
    /**
     * delete share
     * @param id of the share
     */
    void deleteShare(int id);
}
