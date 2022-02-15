package JunicornPincer.Repositories;

import JunicornPincer.Food;
import JunicornPincer.FoodCategory;

import java.sql.*;

public class FoodRepository implements AutoCloseable {

    Connection connection;


    public FoodRepository() {
        try {
            connection = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createFoodCategoryTable() {
        try {
            String str = "CREATE TABLE IF NOT EXISTS FoodCategory (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR (255) NOT NULL); ";

            Statement statement = connection.createStatement();
            statement.execute(str);

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void uploadFoodCategories(){
        for (FoodCategory foodCategory: FoodCategory.values()) {
            try {
            String sql="INSERT INTO foodCategory (name) VALUES ('" + foodCategory+"')";
            Statement statement= connection.createStatement();
                statement.execute(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createFoodTable() {
        try {
            String str = "CREATE TABLE IF NOT EXISTS Food (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR (255) NOT NULL, " +
                    "foodCategory INT NOT NULL , " +                       //foreign key
                    "price INT NOT NULL, " +
                    "restaurantID INT," +
                    "FOREIGN KEY(restaurantID) REFERENCES restaurant(id), " +
                    "FOREIGN KEY(foodCategory) REFERENCES FoodCategory(id)) ";               //JOIN
            Statement statement = connection.createStatement();
            statement.execute(str);

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertFood(Food food){
        String sql="INSERT INTO food (name, foodCategory, price, restaurantID) " +
                "VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement= connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, food.getName());
            preparedStatement.setInt(2, food.getFoodCategory().ordinal()+1);
            preparedStatement.setInt(3, food.getPrice());
            preparedStatement.setInt(4, food.getRestaurant().getId());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            food.setId(generatedKey);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
