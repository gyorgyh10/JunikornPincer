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
        String sql = "INSERT INTO address (city, street, number) " +
                "VALUES (?,?,?)";
        String sql2 = "INSERT INTO customer (name, email, password, phoneNumber, addressID) " +
                "VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatement2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, customer.getAddress().getCity());
            preparedStatement.setString(2, customer.getAddress().getStreet());
            preparedStatement.setString(3, customer.getAddress().getNumber());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            customer.getAddress().setId(generatedKey);

            //kezdődik a customer:
            preparedStatement2.setString(1, customer.getName());
            preparedStatement2.setString(2, customer.getEmail());
            preparedStatement2.setString(3, customer.getPassword());
            preparedStatement2.setString(4, customer.getPhoneNumber());
            preparedStatement2.setInt(5, customer.getAddress().getId());

            preparedStatement2.executeUpdate();

            ResultSet rs2 = preparedStatement.getGeneratedKeys();
            int generatedKey2 = 0;
            if (rs2.next()) {
                generatedKey2 = rs2.getInt(1);
            }

            customer.setId(generatedKey2);

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


    public void updateCustomerInfo(Customer customer) {
        String sql = "UPDATE customer SET name=?, email=?, password=?, phoneNumber=? WHERE id=?; " +
                        "UPDATE address SET city=?, street=?, number=? WHERE id=? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPassword());
            preparedStatement.setString(4, customer.getPhoneNumber());
            preparedStatement.setInt(5, customer.getId());


            preparedStatement.setString(6, customer.getAddress().getCity());
            preparedStatement.setString(7, customer.getAddress().getStreet());
            preparedStatement.setString(8, customer.getAddress().getNumber());
            preparedStatement.setInt(9, customer.getAddress().getId());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
