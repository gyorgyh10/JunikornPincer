package JunicornPincer.Repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
