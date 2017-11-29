package dao;

import helpers.JdbcDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final JdbcDAO jdbcDAO;

    @java.beans.ConstructorProperties({"jdbcDAO"})
    public UserDAO(JdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }

    public boolean checkUserLogin(String userName, String password) {

        jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }

            return true;

        }, "SELECT *FROM Users WHERE username=? AND password=?", new String[]{userName, password});
       return true;
    }


}
