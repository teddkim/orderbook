package com.t4.orderbook.dao;
import com.t4.orderbook.dao.OrderDaoDB.OrderMapper;
import com.t4.orderbook.dao.TradeDaoDB.TradeMapper;
import com.t4.orderbook.entities.Order;
import com.t4.orderbook.entities.Trade;
import com.t4.orderbook.entities.User;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author Owner
 */
@Repository
public class UserDaoDB implements UserDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public List<User> getAll() {
        final String SELECT_ALL_USERS = "SELECT * FROM user";
        List<User> users = jdbc.query(SELECT_ALL_USERS, new UserMapper());
        return users;
    }
    
    @Override
    public User getUserById(int id) {
        try {
            final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
            return jdbc.queryForObject(SELECT_USER_BY_ID, new UserMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    @Override
    @Transactional
    public User addUser(User user) {
        final String INSERT_USER = "INSERT INTO user(username, password, coin, dollars) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_USER,
                user.getUsername(),
                user.getPassword(),
                user.getCoin(),
                user.getDollars());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        user.setId(newId);
        return user;
    }
    
    @Override
    @Transactional
    public boolean deleteUserById(int id) {

        updateOrdersToNoUser(id);
        final String DELETE_USER = "DELETE FROM user WHERE id = ?";
        int x = jdbc.update(DELETE_USER, id);
        if (x > 0) {
            return true;
        } else return false;
    }

    private void updateOrdersToNoUser(int id) {
        User noUser = getUserById(1);
        final String GET_ORDERS_BY_USER = "SELECT * FROM OrderTable WHERE userId = ?";
        List<Order> orders = jdbc.query(GET_ORDERS_BY_USER, new OrderMapper(), id);
        for (Order order : orders) {
            order.setUser(noUser);
            updateNoUserOrder(order);
        }
    }

    @Transactional
    public void updateNoUserOrder(Order order) {
        final String UPDATE_ORDER = "UPDATE OrderTable SET userId = ? WHERE id = ?";
        jdbc.update(UPDATE_ORDER, 1, order.getId());
    }

    public static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int index) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setCoin(rs.getBigDecimal("coin"));
            user.setDollars(rs.getBigDecimal("dollars"));
            return user;
        }
    }
}