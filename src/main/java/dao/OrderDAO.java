package dao;

import helpers.JdbcDAO;
import model.Bucket;
import model.Order;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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
        }, "INSERT INTO Orders (id, gun_order_list) VALUES (?,?)",
                id, orderArr);
    }


    public Optional<Order> getOrderContent(String id) {

        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            Order order = new Order().setId(Integer.parseInt(id));
            try(ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    Array array = rs.getArray("gun_order_list");
                    Object []type = (Object [])array.getArray();
                    for (Object gid: type) {
                        order.addGun(Integer.parseInt(gid.toString()));
                    }
                }
                return order;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;

        }, "SELECT * From Orders WHERE id=?", id));

    }
}
