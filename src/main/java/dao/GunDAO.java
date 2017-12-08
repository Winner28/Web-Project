package dao;

import helpers.JdbcDAO;
import model.Gun;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GunDAO {

    private final JdbcDAO jdbcDAO;

    @java.beans.ConstructorProperties({"jdbcDAO"})
    public GunDAO(JdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }

    public List<Gun> getAllGuns() {
        List<Gun> list = new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
            try {
                while (rs.next()) {
                    list.add(new Gun()
                            .setId(rs.getInt("id"))
                            .setName(rs.getString("name"))
                            .setCaliber(rs.getDouble("caliber"))
                            .setPrice(rs.getDouble("price"))

                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, "SELECT * FROM Gun");
        return list;
    }
}
