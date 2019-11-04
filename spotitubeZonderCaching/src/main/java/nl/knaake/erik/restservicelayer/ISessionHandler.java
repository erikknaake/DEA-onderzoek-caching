package nl.knaake.erik.restservicelayer;

/**
 * Interface for confirming if a session is valid and for making a new session
 */
public interface ISessionHandler {
    boolean validateSession(String token);
    String generateNewToken();
    int getTimeoutTime();
}
