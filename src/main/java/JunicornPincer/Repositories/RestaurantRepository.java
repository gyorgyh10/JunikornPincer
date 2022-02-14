package JunicornPincer.Repositories;

import JunicornPincer.Food;
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

    public void insertRestaurant(Restaurant restaurant){
        String sql="INSERT INTO restaurant (name, addressID, phoneNumber, canDeliver) " +
                "VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement= connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
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

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
