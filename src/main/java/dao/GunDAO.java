package dao;

import helpers.JdbcDAO;
import model.Gun;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GunDAO {

    private final JdbcDAO jdbcDAO;

    @java.beans.ConstructorProperties({"jdbcDAO"})
    public GunDAO(JdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }

    public List<Gun> getAllGuns() {
        List<Gun> guns = new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
            try {
                while (rs.next()) {
                    guns.add(new Gun()
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
        return guns;
    }

    public List<Gun> getSelectedGuns(String[] chosen_products) {
        //or use hashmap to avoid the same id :]
        List<Gun> guns = new ArrayList<>();
        StringBuilder whereBuilder = new StringBuilder();
        for (int i = 0; i<chosen_products.length-1; i++) {
            whereBuilder.append("id=").append("?").append(" OR ");
        }

        whereBuilder.append("id=").append("?");

        String SQL ="SELECT * FROM Gun WHERE " + whereBuilder;
        jdbcDAO.withPreparedStatement(preparedStatement -> {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    guns.add(new Gun()
                            .setId(rs.getInt("id"))
                            .setName(rs.getString("name"))
                            .setCaliber(rs.getDouble("caliber"))
                            .setPrice(rs.getDouble("price"))

                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, SQL, chosen_products);


        return guns;
    }

    public void deleteOrderGuns(String []del_guns) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < del_guns.length - 1; i++) {
            sb.append("OR").append("id=?");
        }

        String SQL = "DELETE FROM Gun WHERE id=?" + sb;

        jdbcDAO.withPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        },SQL, del_guns);
    }
}
