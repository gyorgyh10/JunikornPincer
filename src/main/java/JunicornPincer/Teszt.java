package JunicornPincer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Teszt {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/airplanes?useSSL=false&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "Test123!";

    public static void main(String[] args) {


        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String str= "CREATE TABLE IF NOT EXISTS "+ City.KECSKEMÉT+ "(" +
                    "id INT PRIMARY KEY) ";
            Statement statement = connection.createStatement();
            statement.execute(str);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
