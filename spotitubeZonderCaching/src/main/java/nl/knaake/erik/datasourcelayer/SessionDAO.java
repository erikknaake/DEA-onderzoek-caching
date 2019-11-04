package nl.knaake.erik.datasourcelayer;

import nl.knaake.erik.domain.session.ISessionDAO;
import nl.knaake.erik.crosscuttingconcerns.dtos.SessionExpirationDTO;
import nl.knaake.erik.crosscuttingconcerns.utils.DateConverter;
import org.ehcache.Cache;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Allows to save and retrieve session to/from the database and cache
 */
public class SessionDAO extends BasicDAO implements ISessionDAO {

    //private CacheIdentityMap sessionCache;
    //private Cache<String, SessionExpirationDTO> cache;
    /**
     * Initializes the session cache
     */
    public SessionDAO() {
        //cache = CacheIdentityMap.getInstance(String.class, SessionExpirationDTO.class);
        //cache = CacheFactory.getInstance().createCache(CacheFactory.CacheType.SESSION);
    }

    /**
     * Extends the given session a given amount of minutes
     * @param token Session token of the session to extend
     * @param sessionTimeoutTime Time in minutes to extend the session for
     */
    @Override
    public void extendSession(String token, int sessionTimeoutTime) {
        PreparedStatement stmt = getDb().makePreparedStatement("UPDATE SESSION SET expireingDate = DATE_ADD(NOW(), INTERVAL ? MINUTE)");
        try {
            stmt.setInt(1, sessionTimeoutTime);
            SessionExpirationDTO sessionToExtend = new SessionExpirationDTO(token, DateConverter.convertDateToDateTimeString(new Date()));
            sessionToExtend.setToken(token);
            sessionToExtend.setExpirationDate(DateConverter.convertDateToDateTimeString(DateConverter.addMinutes(new Date(), sessionTimeoutTime)));
            //cache.remove(token);
            //cache.put(token, sessionToExtend);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks wether or not the given session is still valid
     * @param token Sessiontoken to check validity for
     * @return True when the session is still valid
     */
    @Override
    public boolean isSessionStillValid(String token) {

//        SessionCacheState cacheResult = isSessionValidFromCache(token);
//        if(cacheResult.equals(SessionCacheState.VALID)) {
//            return true;
//        }
//        else if (cacheResult.equals(SessionCacheState.EXPIRED)) {
//            return false;
//        }

        PreparedStatement stmt = getDb().makePreparedStatement("SELECT * FROM Session WHERE sessionToken = ? AND expireingDate > NOW()");
        try {
            stmt.setString(1, token);
            ResultSet set = stmt.executeQuery();
            if(set.next())
                return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks wether or not the given session is still valid according to the cache
     * @param token Sessiontoken to check
     * @return SessionCacheState::VALID when the token is valid, SessionCacheState::EXPIRED when the session is expired, SessionCacheState::CACHE_MISS when the cache doesnt know if the session is valid
     */
//    private SessionCacheState isSessionValidFromCache(String token) {
//        SessionExpirationDTO cachedSession = (SessionExpirationDTO) cache.get(token);
//        if(cachedSession != null) {
//            Date curTime = new Date();
//            Date invalidationDate = DateConverter.convertStringToDateTime(cachedSession.getExpirationDate());
//            if(invalidationDate != null)
//                if(invalidationDate.after(curTime)) {
//                    return SessionCacheState.VALID;
//                }
//                else {
//                    return SessionCacheState.EXPIRED;
//                }
//        }
//        return SessionCacheState.CACHE_MISS;
//    }

    /**
     * States our cache can be in for any given session
     */
//    enum SessionCacheState {
//        EXPIRED,
//        VALID,
//        CACHE_MISS
//    }
}
