/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.service;

import com.t4.orderbook.dao.ShareDao;
import com.t4.orderbook.entities.Share;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AmanK
 */
public class ShareDaoStub implements ShareDao {

    List<Share> shares = new ArrayList<>();
    int currentId = 1;

    @Override
    public Share getShareById(int id) {
        for (Share share : shares) {
            if (share.getId() == id) {
                Share newShare = new Share();
                newShare.setId(id);
                newShare.setHigh(share.getHigh());
                newShare.setLow(share.getLow());
                newShare.setPercentage(share.getPercentage());
                newShare.setName(share.getName());
                newShare.setPrice(share.getPrice());
                newShare.setSymbol(share.getSymbol());
                newShare.setTickSize(share.getTickSize());
                return newShare;
            }
        }
        return null;
    }

    @Override
    public List<Share> getAllShares() {
        return shares;
    }

    @Override
    public Share addShare(Share share) {
        share.setId(currentId);
        shares.add(share);
        currentId++;
        return share;
    }

    @Override
    public boolean updateShare(Share share) {
        for (Share shareInList : shares) {

            if (shareInList.getId() == share.getId()) {
                shareInList.setName(share.getName());
                shareInList.setSymbol(share.getSymbol());
                shareInList.setTickSize(share.getTickSize());
                shareInList.setPrice(share.getPrice());
                shareInList.setHigh(share.getHigh());
                shareInList.setLow(share.getLow());
                shareInList.setPercentage(share.getPercentage());
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteShare(int id) {
        shares.remove(getShareById(id));
    }

}
