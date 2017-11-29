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

       return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("username");
                    String pass = resultSet.getString("password");
                    System.out.println(name + pass);
                }

            } catch (SQLException e) {
                e.printStackTrace();

            }

            return true;

        }, "SELECT * FROM User WHERE username=?", new String[]{userName, password});

    }


}
