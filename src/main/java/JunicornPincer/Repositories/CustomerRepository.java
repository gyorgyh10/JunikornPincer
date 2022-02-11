package JunicornPincer.Repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
