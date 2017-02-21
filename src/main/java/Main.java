import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        Connection conn;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.fatal("Postgresql driver not found", e);
            return;
        }

        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/newdb",
                    "postgres",
                    "");
        } catch (SQLException e) {
            logger.fatal(e.getMessage(), e);
            return;
        }

    }

}
