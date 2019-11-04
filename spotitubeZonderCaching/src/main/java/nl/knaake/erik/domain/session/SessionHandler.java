package nl.knaake.erik.domain.session;

import nl.knaake.erik.restservicelayer.ISessionHandler;
import org.apache.commons.lang3.RandomStringUtils;

import javax.inject.Inject;

/**
 * Offers logic for managing sessions
 */
public class SessionHandler implements ISessionHandler {

    @Inject
    private ISessionDAO sessionDAO;

    private static final int SESSION_TIMEOUT_TIME = 15;

    /**
     * Validates if a session is still valid and extends the session if so
     * @param token Authentication token of session to validate
     * @return True when the session is still valid, false otherwise
     */
    @Override
    public boolean validateSession(String token) {
        if(sessionDAO.isSessionStillValid(token)) {
            sessionDAO.extendSession(token, SESSION_TIMEOUT_TIME);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Generates a new random token
     * @return Random token
     */
    @Override
    public String generateNewToken() {
        StringBuilder sessionToken = new StringBuilder();
        for(int i = 0; i < 3; i++) {
            sessionToken.append(RandomStringUtils.randomAlphanumeric(4));
            if(i < 2)
                sessionToken.append("-");
        }
        return sessionToken.toString();
    }

    /**
     * @return Returns the time that a session will remain valid
     */
    @Override
    public int getTimeoutTime() {
        return SESSION_TIMEOUT_TIME;
    }
}
