package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Shop",
                    "root",
                    "1234"
            );

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }

        return con;
    }
}
