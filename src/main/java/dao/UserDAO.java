package dao;

import helpers.JdbcDAO;

public class UserDAO {
    private final JdbcDAO jdbcDAO;

    @java.beans.ConstructorProperties({"jdbcDAO"})
    public UserDAO(JdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }

    /*
    **
    * JdbcDao!!!
    *
    * Сюда пихаем все операции с базой
    * create, get, take etc
    *
     */
}
