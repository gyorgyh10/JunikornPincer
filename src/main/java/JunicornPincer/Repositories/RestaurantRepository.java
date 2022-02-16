package JunicornPincer.Repositories;

import JunicornPincer.Address;
import JunicornPincer.Food;
import JunicornPincer.FoodCategory;
import JunicornPincer.Restaurant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                restaurant = new Restaurant(id, resultSet.getString(2),
                        address, resultSet.getString(4),
                        resultSet.getBoolean(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return restaurant;
    }

    public List<Food> allFoodsOfRestaurant(Restaurant restaurant){
        List<Food> foodList = new ArrayList<>();
        String sql = "SELECT f.id, f.name, f.foodCategory, f.price " +
                "FROM food f " +
                "JOIN restaurant r ON f.restaurantID=r.id " +
                "WHERE r.id=?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,restaurant.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
///            1    2       3             4         5
//            id, name, foodCategory, price, restaurantID
            while (resultSet.next()){
                Food food=new Food(resultSet.getInt(1), resultSet.getString(2),
                        FoodCategory.values()[resultSet.getInt(3)-1],
                        resultSet.getInt(4),restaurant);
                foodList.add(food);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return foodList;
    }

    public void updateRestaurantInfo(Restaurant restaurant) {
        String sql = "UPDATE restaurant  SET id=?, name=?, addressID=?, phoneNumber=?, canDeliver=? WHERE id=? " +
                     "UPDATE address SET city=?, street=?, number=? WHERE id=? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, restaurant.getId());
            preparedStatement.setString(2, restaurant.getName());
            preparedStatement.setInt(3, restaurant.getAddress().getId());
            preparedStatement.setString(4, restaurant.getPhoneNumber());
            preparedStatement.setBoolean(5, restaurant.isCanDeliver());
            preparedStatement.setInt(6, restaurant.getId());


            preparedStatement.setString(7, restaurant.getAddress().getCity());
            preparedStatement.setString(8, restaurant.getAddress().getStreet());
            preparedStatement.setString(9, restaurant.getAddress().getNumber());
            preparedStatement.setInt(10, restaurant.getAddress().getId());
            preparedStatement.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void printAll() {
        String sql = "SELECT * FROM restaurant r " +
                "JOIN address a ON a.ID = r.addressID ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                 1   2       (3)           4              5   (6)     7    8       9
//                id, name, addressID, phoneNumber, canDeliver, id, city, street, number
                System.out.println(resultSet.getInt(1) + " | " + resultSet.getString(2) + " | " +
                        resultSet.getString(4) + " | " + resultSet.getBoolean(5) + " | "
                        + resultSet.getString(7) + " | " + resultSet.getString(8) + " | " +
                        resultSet.getString(9));
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
