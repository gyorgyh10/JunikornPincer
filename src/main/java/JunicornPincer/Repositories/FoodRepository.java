package JunicornPincer.Repositories;

import JunicornPincer.Address;
import JunicornPincer.Food;
import JunicornPincer.FoodCategory;
import JunicornPincer.Restaurant;

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

    public Food searchById(int id) {
        Food food = null;
        String sql = "SELECT * FROM food f " +
                "JOIN restaurant r ON r.id =f.restaurantID " +
                "JOIN address a ON a.id=r.addressID " +
                "WHERE f.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //1     2       3           4           5       6    7      8           9           10      11    12    13      14
                //id, name, foodCategory, price, restaurantID, id, name, addressID, phoneNumber, canDeliver, id, city, street, number
                Address address = new Address(resultSet.getInt(11), resultSet.getString(12),
                        resultSet.getString(13), resultSet.getString(14));
                Restaurant restaurant = new Restaurant(resultSet.getInt(6), resultSet.getString(7),
                        address, resultSet.getString(9),
                        resultSet.getBoolean(10));
                food = new Food(id, resultSet.getString(2),
                        FoodCategory.values()[resultSet.getInt(3)-1],
                        resultSet.getInt(4), restaurant);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return food;
    }

    public void updateFoodInfo(Food food) {
        String sql = "UPDATE food  SET id=?, name=?, foodCategory=?, price=?, restaurantID=? WHERE id=? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, food.getId());
            preparedStatement.setString(2, food.getName());
            preparedStatement.setInt(3, food.getFoodCategory().ordinal()+1);
            preparedStatement.setInt(4, food.getPrice());
            preparedStatement.setInt(5, food.getRestaurant().getId());
            preparedStatement.setInt(6, food.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printAll() {
        String sql = "SELECT f.id, f.name, f.foodCategory, f.price, r.name " +
                "FROM food f " +
                "JOIN restaurant r ON f.restaurantID=r.id ";
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                 1        2       3               4       5
//                f.id, f.name, f.foodCategory, f.price, r.name
                System.out.println(resultSet.getInt(1) + " | " + resultSet.getString(2) + " | " +
                        FoodCategory.values()[resultSet.getInt(3)-1] + " | "  + resultSet.getInt(4) + " | " +
                        "restaurant=" + resultSet.getString(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printAllFoodCategory (){
        FoodCategory[] foodCategoriesArray = FoodCategory.values();
        for (int i = 1; i <= foodCategoriesArray.length; i++) {
            System.out.println(i + " - " + foodCategoriesArray[i-1]);
        }

    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
