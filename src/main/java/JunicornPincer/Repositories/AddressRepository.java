package JunicornPincer.Repositories;

import JunicornPincer.Address;

import java.sql.*;

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

    public void insertAddress(Address address){
        String sql="INSERT INTO address (city, street, number) " +
                "VALUES (?,?,?)";
        try (PreparedStatement preparedStatement= connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, address.getCity());
            preparedStatement.setString(2, address.getStreet());
            preparedStatement.setString(3, address.getNumber());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            address.setId(generatedKey);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
