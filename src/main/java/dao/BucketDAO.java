package dao;

import helpers.JdbcDAO;
import model.Bucket;
import model.User;

import java.sql.Array;
import java.sql.PreparedStatement;
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
        /*StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chosen_products.length-1; i++) {
            sb.append(", (?)");
        }*/
        String SQL = "INSERT INTO Bucket (id, gun_id) VALUES (?,?)";
      /*  try {
            PreparedStatement preparedStatement = jdbcDAO.get().prepareStatement(SQL);
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.setArray(2, products_array);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }*/

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
