package dao;

import helpers.JdbcDAO;

public class OrderDAO {

    private final JdbcDAO jdbcDAO;

    @java.beans.ConstructorProperties({"jdbcDAO"})
    public OrderDAO(JdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }
}
