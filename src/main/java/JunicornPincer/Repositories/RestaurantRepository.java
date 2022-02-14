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
        String sql = "INSERT INTO restaurant (name, addressID, phoneNumber, canDeliver) " +
                "VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, restaurant.getName());
            preparedStatement.setInt(2, restaurant.getAddress().getId());
            preparedStatement.setString(3, restaurant.getPhoneNumber());
            preparedStatement.setBoolean(4, restaurant.isCanDeliver());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            restaurant.setId(generatedKey);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

//    public Restaurant searchById(int id) {
//        Restaurant restaurant = new Restaurant();                     TODO
//        String sql = "SELECT * FROM restaurant r" +
//                "LEFT JOIN food f ON f.restaurantID = r.ID " +
//                "WHERE r.id = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                restaurant = new Restaurant(id, resultSet.getString("c_name"), resultSet.getString("c_email"),
//                        resultSet.getString("c_password"), resultSet.getString("c_phoneNumber"),
//                        new Address(resultSet.getInt("c_addressID"), resultSet.getString("a_city"),
//                                resultSet.getString("a_street"), resultSet.getString("a_number")));
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return restaurant;
//    }

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
