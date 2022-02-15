package JunicornPincer.Repositories;

import JunicornPincer.Address;
import JunicornPincer.Restaurant;

import java.sql.*;

public class RestaurantRepository implements AutoCloseable {
    Connection connection;


    public RestaurantRepository() {
        try {
            connection = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createRestaurantTable() {
        try {
            String str = "CREATE TABLE IF NOT EXISTS Restaurant (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR (255) NOT NULL, " +
                    "addressID INT NOT NULL, " +
                    "phoneNumber VARCHAR (255) NOT NULL, " +
                    "canDeliver BOOLEAN DEFAULT 0, " +
                    "FOREIGN KEY(addressID) REFERENCES address(id)); ";
            Statement statement = connection.createStatement();
            statement.execute(str);

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRestaurant(Restaurant restaurant) {
        String sql = "INSERT INTO address (city, street, number) " +
                "VALUES (?,?,?)";
        String sql2 = "INSERT INTO restaurant (name, addressID, phoneNumber, canDeliver) " +
                "VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatement2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, restaurant.getAddress().getCity());
            preparedStatement.setString(2, restaurant.getAddress().getStreet());
            preparedStatement.setString(3, restaurant.getAddress().getNumber());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            restaurant.getAddress().setId(generatedKey);

            //innentől restaurant


            preparedStatement2.setString(1, restaurant.getName());
            preparedStatement2.setInt(2, restaurant.getAddress().getId());
            preparedStatement2.setString(3, restaurant.getPhoneNumber());
            preparedStatement2.setBoolean(4, restaurant.isCanDeliver());

            preparedStatement2.executeUpdate();
            ResultSet rs2 = preparedStatement2.getGeneratedKeys();
            int generatedKey2 = 0;
            if (rs2.next()) {
                generatedKey2 = rs2.getInt(1);
            }

            restaurant.setId(generatedKey2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Restaurant searchById(int id) {
        Restaurant restaurant = null;
        String sql = "SELECT * FROM restaurant r " +
                "JOIN address a ON a.id =r.addressID " +
                "WHERE r.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //1     2       3           4           5       6    7      8       9
                //id, name, addressID, phoneNumber, canDeliver, id, city, street, number
                Address address = new Address(resultSet.getInt(6), resultSet.getString(7),
                        resultSet.getString(8), resultSet.getString(9));
                restaurant = new Restaurant(1, resultSet.getString(2),
                        address, resultSet.getString(4),
                        resultSet.getBoolean(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return restaurant;
    }


    //    TODO
    public void updateRestaurantInfo(Restaurant restaurant) {
        String sql = "UPDATE restaurant  SET name=?, email=?, password=?, phoneNumber=? WHERE id=? ";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, restaurant.getCity());
//            preparedStatement.setString(2, restaurant.getStreet());
//            preparedStatement.setString(3, restaurant.getNumber());
//            preparedStatement.setInt(4, restaurant.getId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }

    public void updateRestaurantAddress(Address address) {

    }

    public void printAll() {
        String sql = "SELECT * FROM restaurant c " +
                "JOIN address a ON a.ID = c.addressID ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " | " + resultSet.getString(2) + " | " +
                        resultSet.getString(3) + " | " + resultSet.getString(4) + " | "
                        + resultSet.getString(5) + " | " + resultSet.getInt(6) + " | "
                        + resultSet.getString(7) + " | " + resultSet.getString(8) + " | " + resultSet.getString(9)
                        + " | " + resultSet.getString(10));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
