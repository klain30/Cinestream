import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cinestream";
    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASSWORD = "";  // Replace with your MySQL password

    public static Connection connect() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unable to connect to the database.");
        }
    }
}
