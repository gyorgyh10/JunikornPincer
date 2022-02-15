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

    public void insertAddress(Address address) {                                    //tudjuk h nem használjuk
        String sql = "INSERT INTO address (city, street, number) " +
                "VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    public Address searchById(int id) {
        Address address = null;
        String sql = "SELECT city, street, number FROM address " +
                "WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                address = new Address(id, resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return address;
    }

    public void updateById(Address address) {
        String sql = "UPDATE address  SET city=?, street=?, number=? WHERE id=? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, address.getCity());
            preparedStatement.setString(2, address.getStreet());
            preparedStatement.setString(3, address.getNumber());
            preparedStatement.setInt(4, address.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void printAll() {
        String sql = "SELECT * FROM address";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " | " + resultSet.getString(2) + " | " +
                        resultSet.getString(3) + " | " + resultSet.getString(4));
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
