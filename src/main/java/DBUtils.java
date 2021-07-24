import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DBUtils {

        private static final String DB_URL = System.getenv("dbUrl");
        private static final String DB_USER = System.getenv("dbUser");
        private static final String DB_PASS = System.getenv("dbPass");
        private static final String DB_PARAMS = System.getenv("dbParams");

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(DB_URL + DB_PARAMS, DB_USER, DB_PASS);
        }


}
