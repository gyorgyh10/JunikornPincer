package JunicornPincer.Repositories;

import JunicornPincer.Orders;

import java.sql.*;

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
    }


    public void dateToSQL() {
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

}
