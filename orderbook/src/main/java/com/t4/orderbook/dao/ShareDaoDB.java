/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.Share;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author TaehyunKim
 */
@Repository
public class ShareDaoDB implements ShareDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Share getShareById(int id) {
        try {
            final String GET_SHARE_BY_ID = "SELECT * FROM share WHERE id = ?";
            return jdbc.queryForObject(GET_SHARE_BY_ID, new ShareMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Share> getAllShares() {
        final String GET_ALL_SHARES = "SELECT * FROM share";
        return jdbc.query(GET_ALL_SHARES, new ShareMapper());
    }

    @Override
    @Transactional
    public Share addShare(Share share) {
        final String INSERT_SHARE = "INSERT INTO share(name, symbol, tickSize, percentage, high, low, price) " +
                "VALUES(?,?,?,?,?,?,?)";
        jdbc.update(INSERT_SHARE,
                share.getName(),
                share.getSymbol(),
                share.getTickSize(),
                share.getPercentage(),
                share.getHigh(),
                share.getLow(),
                share.getPrice());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        share.setId(newId);
        return share;
    }

    @Override
    public boolean updateShare(Share share) {
        final String UPDATE_SHARE = "UPDATE share SET name = ?, symbol = ?, tickSize = ?, percentage = ?, high = ?, low = ?, price = ?" +
                " WHERE id = ?";
        int success = jdbc.update(UPDATE_SHARE,
                share.getName(),
                share.getSymbol(),
                share.getTickSize(),
                share.getPercentage(),
                share.getHigh(),
                share.getLow(),
                share.getPrice(),
                share.getId());
        return(success > 0);
    }

    @Override
    @Transactional
    public void deleteShare(int id) {
        final String DELETE_SHARE = "DELETE FROM share WHERE id = ?";
        jdbc.update(DELETE_SHARE, id);
    }
    
    public static final class ShareMapper implements RowMapper<Share> { 
        
        @Override
        public Share mapRow(ResultSet rs, int index) throws SQLException {
            Share share = new Share();
            share.setId(rs.getInt("id"));
            share.setName(rs.getString("name"));
            share.setSymbol(rs.getString("symbol"));
            share.setTickSize(rs.getBigDecimal("tickSize"));
            share.setPercentage(rs.getBigDecimal("percentage"));
            share.setHigh(rs.getBigDecimal("high"));
            share.setLow(rs.getBigDecimal("low"));
            share.setPrice(rs.getBigDecimal("price"));
            
            return share;
        }
    }
}
