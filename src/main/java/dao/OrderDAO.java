package dao;

import helpers.JdbcDAO;

import java.sql.Array;
import java.sql.SQLException;

public class OrderDAO {

    private final JdbcDAO jdbcDAO;

    @java.beans.ConstructorProperties({"jdbcDAO"})
    public OrderDAO(JdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }


    public void addOrder(String id, String []order_list) {
        Array orderArr = null;
        try {
            orderArr = jdbcDAO.get().createArrayOf("products", order_list);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        jdbcDAO.withPreparedStatement(preparedStatement -> {
                    try {
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
        }, "INSERT INTO Order (id, gun_order_list) VALUES (?,?)",
                id, orderArr);
    }
}
