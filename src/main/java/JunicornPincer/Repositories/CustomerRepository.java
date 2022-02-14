package JunicornPincer.Repositories;

import JunicornPincer.Address;
import JunicornPincer.Customer;

import java.sql.*;

public class CustomerRepository implements AutoCloseable {
    Connection connection;


    public CustomerRepository() {
        try {
            connection = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createCustomerTable() {
    try {
        String str= "CREATE TABLE IF NOT EXISTS Customer (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR (255) NOT NULL, " +
                "email VARCHAR (255) NOT NULL, " +
                "password VARCHAR (255) NOT NULL, " +
                "phoneNumber VARCHAR (255) NOT NULL, " +
                "addressID INT NOT NULL, " +
                "FOREIGN KEY(AddressID) REFERENCES Address(id)); ";
        Statement statement = connection.createStatement();
        statement.execute(str);

    } catch (
    SQLException e) {
        e.printStackTrace();
    }
    }

    public void insertCustomer(Customer customer){
        String sql="INSERT INTO customer (name, email, password, phoneNumber, addressID) " +
                "VALUES (?,?,?,?,?)";
//        PreparedStatement ps = connection.prepareStatement(sql,
//                Statement.RETURN_GENERATED_KEYS);

        try (PreparedStatement preparedStatement= connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPassword());
            preparedStatement.setString(4, customer.getPhoneNumber());

            preparedStatement.setInt(5, customer.getAddress().getId());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

//            System.out.println("Inserted record's ID: " + generatedKey);
            customer.setId(generatedKey);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
