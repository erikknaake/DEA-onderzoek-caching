package nl.knaake.erik.datasourcelayer.database;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for reading the file: database.properties
 * So we can get the correct driver and connection string for the database
 */
public class DatabaseProperties {
    private Logger logger = Logger.getLogger(getClass().getName());
    private Properties properties;
    private static DatabaseProperties instance;

    /**
     * Gets the instance of DatabaseProperties, by doing this this way the file isnt read more than once
     * @return instance of DatabaseProperties
     */
    public static DatabaseProperties getInstance() {
        if(instance == null)
            instance = new DatabaseProperties();
        return instance;
    }

    private DatabaseProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't access property file database.properties", e);
        }
    }

    /**
     * Gets the driver string
     * @return driver identifier string
     */
    public String driver()
    {
        return properties.getProperty("driver");
    }

    /**
     * Gets the connection string for the database
     * @return connection string for the database
     */
    public String connectionString()
    {
        return properties.getProperty("connectionString");
    }

}