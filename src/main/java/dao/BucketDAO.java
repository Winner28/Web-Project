package dao;

import helpers.JdbcDAO;
import model.Bucket;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class BucketDAO {

    private final JdbcDAO jdbcDAO;

    @java.beans.ConstructorProperties({"jdbcDAO"})
    public BucketDAO(JdbcDAO jdbcDAO) {
        this.jdbcDAO = jdbcDAO;
    }


    public void addGuns(String id, String[] chosen_products) {
        Array products_array = null;

        try {
             products_array = jdbcDAO.get().createArrayOf("products", chosen_products);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String SQL = "INSERT INTO Bucket (id, gun_id) VALUES (?,?)";

        jdbcDAO.withPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } , SQL, id, products_array);
    }


    public Optional<Bucket> getBucketContent(String id) {

        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            Bucket bucket = new Bucket().setId(Integer.parseInt(id));
            try(ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    Array array = rs.getArray("gun_id");
                    Object []type = (Object [])array.getArray();
                    for (Object gid: type) {
                        bucket.addGun(Integer.parseInt(gid.toString()));
                    }
                }
                return bucket;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;

        }, "SELECT * From Bucket WHERE id=?", id));

    }

    public boolean deleteAllData(String id) {
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }, "DELETE FROM Bucket WHERE id=?", id);
    }
}
