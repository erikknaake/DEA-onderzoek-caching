package nl.knaake.erik.datasourcelayer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Offers communication with the database via preparedstatements
 */
public class Database implements IDatabase {

    private Logger logger = Logger.getLogger(getClass().getName());
    private Connection connection;

    private DatabaseProperties databaseProperties;

    /**
     * Initializes the database driver and setups the properties for any connection to the database
     */
    public Database()
    {
        databaseProperties = DatabaseProperties.getInstance();
        tryLoadJdbcDriver(databaseProperties);
    }

    /**
     * Loads the database driver
     * @param databaseProperties properties that contains the driver to user
     */
    private void tryLoadJdbcDriver(DatabaseProperties databaseProperties) {
        try {
            Class.forName(databaseProperties.driver());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Can't load JDBC Driver " + databaseProperties.driver(), e);
        }
    }

    /**
     * Makes a prepared statement on a connection for a given sql query
     * NOTE: when this method is called <code>closeConnection</code> should be called too to close the newly made connections
     * @param sql sql the insert into the prepared statement
     * @return Prepared statement that has been bound to a connection and contains the sql query
     */
    @Override
    public PreparedStatement makePreparedStatement(String sql) {
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection(databaseProperties.connectionString());
            statement = connection.prepareStatement(sql);
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return statement;
    }

    /**
     * Closes the current connection
     */
    @Override
    public void closeConnection() {
        if(connection != null)
            try {
                connection.close();
            }
            catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing connection ", e);
            }
    }
}
