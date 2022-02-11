package JunicornPincer.Repositories;

import JunicornPincer.Repositories.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class FoodRepository implements AutoCloseable {

    Connection connection;


    public FoodRepository() {
        try {
            connection = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createFoodCategoriesTable(){
        try {
            String str = "CREATE TABLE IF NOT EXISTS FoodCategories (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR (255) NOT NULL); ";

            Statement statement = connection.createStatement();
            statement.execute(str);

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }


    public void createFoodTable() {
        try {
            String str = "CREATE TABLE IF NOT EXISTS Food (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR (255) NOT NULL, " +
                    "foodCategories INT NOT NULL , " +                       //foreign key
                    "price INT NOT NULL, " +
                    "restaurantID INT," +
                    "FOREIGN KEY(restaurantID) REFERENCES restaurant(id), " +
                    "FOREIGN KEY(foodCategories) REFERENCES FoodCategories(id)) ";               //JOIN
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
