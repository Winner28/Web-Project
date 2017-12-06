package dao;

import helpers.JdbcDAO;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDAO {
    private final JdbcDAO jdbcDAO;

    @java.beans.ConstructorProperties({"jdbcDAO"})
    public UserDAO(JdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }

    public Optional<User> checkUser(String userName, String password) {

       return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String pass = resultSet.getString("password");
                    String name = resultSet.getString("name");
                    int id = resultSet.getInt("id");
                    if (userName.equals(username) && password.equals(pass)) {
                        return new User()
                                .setId(id)
                                .setUserName(userName)
                                .setPassword(password)
                                .setName(name);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }, "SELECT * FROM User WHERE username=?", new String[]{userName}));

    }


    public boolean registerUser(String name, String username, String password) {
       return !jdbcDAO.mapPreparedStatement(preparedStatement -> checkUser(username,password).isPresent(),
               "INSERT INTO User (name, username, password) VALUES (?, ?, ?)",
                new String[]{name, username, password });
    }

}
