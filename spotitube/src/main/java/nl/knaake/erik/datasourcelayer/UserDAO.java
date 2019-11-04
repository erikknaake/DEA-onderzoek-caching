package nl.knaake.erik.datasourcelayer;

import nl.knaake.erik.crosscuttingconcerns.dtos.SessionExpirationDTO;
import nl.knaake.erik.crosscuttingconcerns.dtos.UserDTO;
import nl.knaake.erik.crosscuttingconcerns.utils.DateConverter;
import nl.knaake.erik.domain.login.IUserDAO;
import nl.knaake.erik.restservicelayer.ISessionHandler;
import org.ehcache.Cache;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Offers logic to edit and query users to the database
 */
public class UserDAO extends BasicDAO implements IUserDAO {

    @Inject
    private ISessionHandler sessionHandler;

    //private CacheIdentityMap cache;
    private Cache<String, SessionExpirationDTO> cache;

    /**
     * Initializes the sessioncache
     */
    public UserDAO() {
        //cache = CacheIdentityMap.getInstance(String.class, SessionExpirationDTO.class);

        cache = CacheFactory.getInstance().createCache(CacheFactory.CacheType.SESSION);
    }

    /**
     * Get a user by its username
     * @param username Username to find a user for
     * @return password, salt, user and sessiontoken that belong to the given username
     */
    @Override
    public UserDTO getUserDTOByName(String username) {
        PreparedStatement stmt = getDb().makePreparedStatement("SELECT password, salt, user, sessionToken FROM User WHERE user = ?");
        UserDTO user = new UserDTO();
        try {
            stmt.setString(1, username);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                user.setUser(set.getString("user"));
                user.setToken(set.getString("sessionToken"));
                user.setPassword(set.getString("password"));
                user.setSalt(set.getString("salt"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
        return user;
    }

    /**
     * Adds a new session to the cache and database
     * @param user User to register a new session for
     */
    @Override
    public void addNewSession(UserDTO user) {
        user.setToken(sessionHandler.generateNewToken());
        registerSession(user.getToken(), user.getUser());
    }

    /**
     * Adds a session to the database
     * @param token Sessiontoken to add to the database
     * @param user Username of the user who owns the given session
     */
    private void registerSession(String token, String user) {
        PreparedStatement updateUser = getDb().makePreparedStatement("UPDATE User set sessionToken = ? WHERE user = ?");
        PreparedStatement insertSession = getDb().makePreparedStatement("INSERT INTO Session (sessionToken, expireingDate) VALUES (?, DATE_ADD(NOW(), INTERVAL ? MINUTE))");
        try {
            deleteOldSessionIfExists(user);

            updateUser.setString(1, token);
            updateUser.setString(2, user);
            updateUser.execute();
            updateUser.close();

            insertSession.setString(1, token);
            insertSession.setInt(2, sessionHandler.getTimeoutTime());
            insertSession.execute();
            insertSession.close();

            addNewSessionToCache(token);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        getDb().closeConnection();
    }

    /**
     * Adds a session to the cache
     * @param token Token to add to the cache
     */
    private void addNewSessionToCache(String token) {
        cache.put(token, new SessionExpirationDTO(token, DateConverter.convertDateToDateTimeString(DateConverter.addMinutes(new Date(), sessionHandler.getTimeoutTime()))));
    }

    /**
     * Delete a session
     * @param user Username of the user to delete its session for
     * @throws SQLException Should only be thrown if there is somekind of communication error with the database
     */
    private void deleteOldSessionIfExists(String user) throws SQLException {
        PreparedStatement hasSessionQuery = getDb().makePreparedStatement("SELECT * FROM User WHERE sessionToken IS NOT NULL AND user = ?");
        hasSessionQuery.setString(1, user);
        ResultSet hasSessionSet = hasSessionQuery.executeQuery();
        if(hasSessionSet.next()) {
            PreparedStatement deleteSession = getDb().makePreparedStatement("DELETE FROM Session WHERE sessionToken = (SELECT sessionToken FROM User where user = ?)");
            deleteSession.setString(1, user);
            deleteSession.executeUpdate();
            deleteSession.close();
            cache.remove(hasSessionSet.getString("sessionToken"));
        }
        getDb().closeConnection();
    }
}
