package dao;

import helpers.JdbcDAO;
import lombok.SneakyThrows;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {
    private final JdbcDAO jdbcDAO;

    @java.beans.ConstructorProperties({"jdbcDAO"})
    public UserDAO(JdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }

    public Optional<User> checkUserLogin(String userName, String password) {

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

        }, "SELECT * FROM USER WHERE username=?", new String[]{userName}));

    }


    public boolean registerUser(String name, String username, String password) {
       return jdbcDAO.mapPreparedStatement(preparedStatement -> {
                   if (checkIfUserRegister(username)) {
                       System.out.println("Cant create user with same username");
                       return false;
                   }

                   try {
                       preparedStatement.executeUpdate();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }


                   return true;

               },
               "INSERT INTO USER (name, username, password) VALUES (?, ?, ?)",
                new String[]{name, username, password });
    }

    private boolean checkIfUserRegister(String username) {
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return true;
            }
        }, "SELECT *FROM USER WHERE username=?", new String[]{username});

    }


    public List<User> getAll() {
        
        List<User> users = new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
                    try {
                        while (rs.next())
                            users.add(new User()
                                    .setId(rs.getInt("id"))
                                    .setName(rs.getString("name"))
                                    .setPassword(rs.getString("password"))
                                    .setUserName(rs.getString("username"))
                            );
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                },
                "SELECT * FROM User");

        return users;
    }

}
