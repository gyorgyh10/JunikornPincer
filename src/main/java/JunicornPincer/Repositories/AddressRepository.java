package JunicornPincer.Repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressRepository implements AutoCloseable {

    Connection connection;


    public AddressRepository() {
        try {
            connection = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAddressTable() {
        try {
            String str = "CREATE TABLE IF NOT EXISTS Address (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "city VARCHAR (255) NOT NULL, " +
                    "street VARCHAR (255) NOT NULL, " +
                    "number VARCHAR (100) NOT NULL); ";
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
