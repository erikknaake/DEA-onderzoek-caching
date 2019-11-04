package nl.knaake.erik.domain.session;

/**
 * Allows communication with persistence for sessions
 */
public interface ISessionDAO {
    void extendSession(String token, int sessionTimeoutTime);
    boolean isSessionStillValid(String token);
}
