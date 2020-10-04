/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t4.orderbook.dao;

import com.t4.orderbook.entities.OpeningPrices;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
public class OpeningPricesDaoDB implements OpeningPricesDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public OpeningPrices getOpeningPrice(LocalDateTime datetime, int shareId) {
        try {
            final String GET_OPENING_PRICES_BY_ID = "SELECT * FROM openingprices WHERE datetime = ? AND shareId = ?";
            return jdbc.queryForObject(GET_OPENING_PRICES_BY_ID, new OpeningPricesMapper(), datetime, shareId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<OpeningPrices> getAllOpeningPrices() {
        final String GET_ALL_OPRNING_PRICES = "SELECT * FROM openingprices";
        return jdbc.query(GET_ALL_OPRNING_PRICES, new OpeningPricesMapper());
    }

    @Override
    @Transactional
    public OpeningPrices addOpeningPrices(OpeningPrices openingPrices) {
        final String INSERT_OPENING_PRICES = "INSERT INTO openingprices(datetime, price, shareId) " +
                "VALUES(?,?,?)";
        jdbc.update(INSERT_OPENING_PRICES,
                openingPrices.getDatetime(),
                openingPrices.getPrice(),
                openingPrices.getShareId());
        
        return openingPrices;
    }

    @Override
    @Transactional
    public void deleteOpeningPrices(LocalDateTime datetime, int shareId) {
        final String DELETE_OPENING_PRICE = "DELETE FROM openingprices WHERE datetime = ? AND shareId = ?";
        jdbc.update(DELETE_OPENING_PRICE, datetime, shareId);
    }
    
    public static final class OpeningPricesMapper implements RowMapper<OpeningPrices> {

        @Override
        public OpeningPrices mapRow(ResultSet rs, int index) throws SQLException {
            OpeningPrices openingPrices = new OpeningPrices();
            openingPrices.setDatetime(rs.getTimestamp("datetime").toLocalDateTime());
            openingPrices.setPrice(rs.getBigDecimal("price"));
            openingPrices.setShareId(rs.getInt("shareId"));
            
            return openingPrices;
        }
    }
}
