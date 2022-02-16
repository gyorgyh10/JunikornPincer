package JunicornPincer.Repositories;

import JunicornPincer.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersRepository implements AutoCloseable {
    Connection connection;


    public OrdersRepository() {
        try {
            connection = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createOrdersConnectorTable() {
        try {
            String str = "CREATE TABLE IF NOT EXISTS OrdersConnector (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "OrdersID INT NOT NULL, " +
                    "FoodID INT NOT NULL, " +
                    "FOREIGN KEY(OrdersID) REFERENCES Food(id), " +
                    "FOREIGN KEY(FoodID) REFERENCES Orders(id)) ";
            Statement statement = connection.createStatement();
            statement.execute(str);

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrdersTable() {
        try {
            String str = "CREATE TABLE IF NOT EXISTS Orders (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "date DATE NOT NULL, " +
                    "CustomerID INT NOT NULL, " +
                    "FOREIGN KEY(CustomerID) REFERENCES customer(id)); ";
            Statement statement = connection.createStatement();
            statement.execute(str);

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertOrders(Orders orders) {
        String sql = "INSERT INTO orders (date, CustomerID) " +
                "VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, orders.getDate());
            preparedStatement.setInt(2, orders.getCustomer().getId());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            orders.setId(generatedKey);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Food> foodList = orders.getFoodList();
        String sql2 = "INSERT INTO ordersconnector (ordersID, FoodID) " +
                "VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {

            for (int i = 0; i < foodList.size(); i++) {
                preparedStatement.setInt(1, orders.getId());
                preparedStatement.setInt(2, foodList.get(i).getId());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Orders searchById(int id) {
        Orders orders = null;
        String sql = "SELECT *, count(f.id) AS quantity FROM orders o " +
                "JOIN customer c ON c.id=o.CustomerID " +
                "JOIN address ac ON c.addressID=ac.id " +
                "JOIN ordersconnector oc ON o.id=oc.OrdersID " +
                "JOIN food f ON f.id=oc.FoodID " +
                "JOIN restaurant r ON r.id=f.restaurantID " +
                "JOIN address ar ON ar.id=r.addressID " +
                "WHERE o.id=? " +
                "GROUP BY f.id;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Food> foodList=new ArrayList<>();
            while (resultSet.next()) {

//                1     2       3       4     5     6       7           8           9      10   11      12      13   14
//                id, date, CustomerID, id, name, email, password, phoneNumber, addressID, id, city, street, number, id,
//
//                   15       16    17   18         19       20         21       22   23        24       25
//                OrdersID, FoodID, id, name, foodCategory, price, restaurantID, id, name, addressID, phoneNumber,
//
//                    26      27   28   29      30        31
//                canDeliver, id, city, street, number, quantity

                Address addressR = new Address(resultSet.getInt(27), resultSet.getString(28),
                        resultSet.getString(29), resultSet.getString(30));
                Restaurant restaurant = new Restaurant(resultSet.getInt(22), resultSet.getString(23),
                        addressR, resultSet.getString(25),
                        resultSet.getBoolean(26));
                Food food = new Food(resultSet.getInt(17), resultSet.getString(18),
                        FoodCategory.values()[resultSet.getInt(19)-1],
                        resultSet.getInt(20), restaurant);
                Address addressC = new Address(resultSet.getInt(10), resultSet.getString(11),
                        resultSet.getString(12), resultSet.getString(13));
                Customer customer = new Customer(resultSet.getInt(4), resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8), addressC);

                for (int i = 1; i <= resultSet.getInt(31); i++) {
                    foodList.add(food);
                }
                orders = new Orders(id, resultSet.getDate(2), foodList, customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    public void dateToSQL() {
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

}
