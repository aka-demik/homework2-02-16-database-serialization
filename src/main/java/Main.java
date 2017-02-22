import org.apache.log4j.Logger;
import persistent.xml.SerializationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        if (args.length == 0) {
            System.out.println("Usage: app import|export");
            return;
        }

        logger.info("Application started");
        Connection conn;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.fatal("Postgresql driver not found", e);
            return;
        }

        logger.info("Connecting to database");
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/newdb",
                    "postgres",
                    "sergtsop");
        } catch (SQLException e) {
            logger.fatal(e.getMessage(), e);
            return;
        }

        SerializationManager manager = new SerializationManager(conn);

        switch (args[0]) {
            case "import":
                logger.info("Importing data");
                manager.importAll();
                break;

            case "export":
                logger.info("Exporting data");
                manager.exportAll();
                break;
        }
        logger.info("Done");
    }
}
