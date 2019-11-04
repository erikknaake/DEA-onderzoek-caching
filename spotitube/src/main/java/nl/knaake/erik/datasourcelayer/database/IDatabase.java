package nl.knaake.erik.datasourcelayer.database;

import java.sql.PreparedStatement;

/**
 * Offers functionality to communicate with a database via preparedstatements
 */
public interface IDatabase {
    PreparedStatement makePreparedStatement(String sql);
    void closeConnection();
}
