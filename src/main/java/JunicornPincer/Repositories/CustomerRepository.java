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
            String str = "CREATE TABLE IF NOT EXISTS Customer (" +
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

    public void insertCustomer(Customer customer) {
        String sql = "INSERT INTO customer (name, email, password, phoneNumber, addressID) " +
                "VALUES (?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    public Customer searchById(int id) {
        Customer customer = null;
        String sql = "SELECT c.name AS c_name, c.email AS c_email, c.password AS c_password, " +
                "c.phoneNumber AS c_phoneNumber, c.addressID AS c_addressID, a.city AS a_city, " +
                "a.street AS a_street, a.number AS a_number FROM customer c " +
                "JOIN address a ON a.id =c.addressID " +
                "WHERE c.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer = new Customer(id, resultSet.getString("c_name"), resultSet.getString("c_email"),
                        resultSet.getString("c_password"), resultSet.getString("c_phoneNumber"),
                        new Address(resultSet.getInt("c_addressID"), resultSet.getString("a_city"),
                                resultSet.getString("a_street"), resultSet.getString("a_number")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    //    TODO
    public void updateCustomerInfo(Customer customer) {
        String sql = "UPDATE customer  SET name=?, email=?, password=?, phoneNumber=? WHERE id=? ";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, customer.getCity());
//            preparedStatement.setString(2, customer.getStreet());
//            preparedStatement.setString(3, customer.getNumber());
//            preparedStatement.setInt(4, customer.getId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }

    public void updateCustomerAddress(Address address) {

    }

    public void printAll() {
        String sql = "SELECT * FROM customer c " +
                "JOIN address a ON a.ID = c.addressID ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " | " + resultSet.getString(2) + " | " +
                        resultSet.getString(3) + " | " + resultSet.getString(4) + " | "
                        + resultSet.getString(5) + " | " + resultSet.getInt(6) + " | "
                        + resultSet.getString(7) + " | " + resultSet.getString(8) + " | " + resultSet.getString(9)
                        + " | " + resultSet.getString(10));
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
